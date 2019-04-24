package com.blogspot.mvnblogbuild.kalah.core;

import com.blogspot.mvnblogbuild.kalah.domain.Game;
import com.blogspot.mvnblogbuild.kalah.domain.PlayerGameSession;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static java.lang.String.format;

@Component
public class KalahCore {

    public static final Integer INITIAL_STONES_IN_PIT = 6;

    public Game makeAMove(Game game, Integer pitId) {
        validateAndSetGameState(game, pitId);

        PlayerMove currentPlayerMove = game.getPlayerMove();

        // Extract stones from players pit
        PlayerGameSession playerGameSession = getPlayerMoveByPitId(pitId) == PlayerMove.NORTH ? game.getNorthPlayer() : game.getSouthPlayer();
        int currentStones = playerGameSession.getPits()[pitIdToPlayerPitsPosition(pitId)];
        if (currentStones == 0) {
            throw new IllegalStateException(format("You can't use empty pit %d", pitId));
        }
        playerGameSession.getPits()[pitIdToPlayerPitsPosition(pitId)] = 0;

        int currentPitId = pitId + 1;
        while (currentStones > 0) {
            currentStones = processPlayer(game, currentPlayerMove, currentPitId, currentStones);

            // Switch player
            if (currentPlayerMove == PlayerMove.NORTH) {
                currentPlayerMove = PlayerMove.SOUTH;
                currentPitId = 8;
            } else {
                currentPlayerMove = PlayerMove.NORTH;
                currentPitId = 1;
            }
        }

        checkAndSetFinishGameState(game);
        return game;
    }

    private void validateAndSetGameState(Game game, Integer pitId) {
        if (isGameFinished(game)) {
            throw new IllegalStateException("Game is finished. You can't make any move");
        }

        if (isPitIdAKalah(pitId)) {
            throw new IllegalArgumentException(format("Wrong pitId value. PitId %d points to kalah", pitId));
        }

        PlayerMove currentPlayerMove = getPlayerMoveByPitId(pitId);
        if (game.getPlayerMove() == PlayerMove.NONE) {
            game.setPlayerMove(currentPlayerMove);
        } else {
            if (!currentPlayerMove.equals(game.getPlayerMove())) {
                throw new IllegalStateException(format("Wrong game state. %s player must make his move instead of %s player",
                        game.getPlayerMove().name(), currentPlayerMove.name()));
            }
        }
    }

    private int processPlayer(Game game, PlayerMove currentPlayerMove, int currentPitId, int stones) {
        int currentStones = stones;
        PlayerGameSession playerGameSession = currentPlayerMove == PlayerMove.NORTH ? game.getNorthPlayer() : game.getSouthPlayer();
        int pitPosition = pitIdToPlayerPitsPosition(currentPitId);
        while (pitPosition < 6 && currentStones > 0) {
            playerGameSession.getPits()[pitPosition] += 1;
            currentStones--;
            pitPosition++;
        }

        if (game.getPlayerMove() == currentPlayerMove) {
            if (currentStones > 0) {
                playerGameSession.setKalah(playerGameSession.getKalah() + 1);
                currentStones--;
            } else {
                if (playerGameSession.getPits()[pitPosition - 1] == 1) {
                    // When the last stone lands in an own empty pit, the player captures this stone and all stones in the opposite pit (the
                    //other players' pit) and puts them in his own Kalah.

                    // Move back to the last processed pit ID
                    pitPosition--;
                    playerGameSession.getPits()[pitPosition] = 0;
                    PlayerGameSession oppositePlayerGameSession = currentPlayerMove == PlayerMove.NORTH ? game.getSouthPlayer() : game.getNorthPlayer();
                    int oppositePlayerStones = oppositePlayerGameSession.getPits()[5 - pitPosition];
                    oppositePlayerGameSession.getPits()[5 - pitPosition] = 0;

                    playerGameSession.setKalah(playerGameSession.getKalah() + 1 + oppositePlayerStones);
                }

                switchGamePlayer(game);
            }
        } else {
            if (currentStones == 0) {
                switchGamePlayer(game);
            }
        }

        return currentStones;
    }

    private void switchGamePlayer(Game game) {
        game.setPlayerMove(game.getPlayerMove() == PlayerMove.NORTH ? PlayerMove.SOUTH : PlayerMove.NORTH);
    }

    private void checkAndSetFinishGameState(Game game) {
        boolean northPlayerHasntStones = isAllPitsAreEmpty(game.getNorthPlayer());
        boolean southPlayerHasntStones = isAllPitsAreEmpty(game.getSouthPlayer());
        if (northPlayerHasntStones || southPlayerHasntStones) {
            game.setPlayerMove(PlayerMove.FINISHED);
        }
    }

    private boolean isAllPitsAreEmpty(PlayerGameSession player) {
        return !Stream.of(player.getPits())
                .filter(pit -> pit > 0)
                .findFirst()
                .isPresent();
    }

    private int pitIdToPlayerPitsPosition(Integer pitId) {
        return pitId < 7 ? pitId - 1 : pitId - 8;
    }

    private boolean isGameFinished(Game game) {
        return game.getPlayerMove() == PlayerMove.FINISHED;
    }

    private boolean isPitIdAKalah(Integer pitId) {
        return pitId == 7 || pitId == 14;
    }

    private PlayerMove getPlayerMoveByPitId(Integer pitId) {
        return pitId < 7 ? PlayerMove.NORTH: PlayerMove.SOUTH;
    }

}
