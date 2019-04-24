package com.blogspot.mvnblogbuild.kalah.core;

import com.blogspot.mvnblogbuild.kalah.domain.Game;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class KalahCore {

    public static final Integer INITIAL_STONES_IN_PIT = 6;

    public Game makeAMove(Game game, Integer pitId) {
        validateGameState(game, pitId);
        return game;
    }

    private void validateGameState(Game game, Integer pitId) {
        if (isPitIdAKalah(pitId)) {
            throw new IllegalArgumentException(format("Wrong pitId value. PitId %d points to kalah", pitId));
        }

        PlayerMove currentPlayerMove = getPlayerMoveByPitId(pitId);
        if (!currentPlayerMove.equals(game.getPlayerMove())) {
            throw new IllegalStateException(format("Wrong game state. %s player must make his move instead of %s player",
                    game.getPlayerMove().name(), currentPlayerMove.name()));
        }
    }

    private boolean isPitIdAKalah(Integer pitId) {
        return pitId == 7 || pitId == 14;
    }

    private PlayerMove getPlayerMoveByPitId(Integer pitId) {
        return pitId < 7 ? PlayerMove.NORTH: PlayerMove.SOUTH;
    }

}
