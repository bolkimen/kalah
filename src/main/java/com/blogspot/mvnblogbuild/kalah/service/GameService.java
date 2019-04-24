package com.blogspot.mvnblogbuild.kalah.service;

import com.blogspot.mvnblogbuild.kalah.core.KalahCore;
import com.blogspot.mvnblogbuild.kalah.core.PlayerMove;
import com.blogspot.mvnblogbuild.kalah.domain.Game;
import com.blogspot.mvnblogbuild.kalah.domain.PlayerGameSession;
import com.blogspot.mvnblogbuild.kalah.dto.DTOConverterUtil;
import com.blogspot.mvnblogbuild.kalah.dto.GameDTO;
import com.blogspot.mvnblogbuild.kalah.dto.GameStateDTO;
import com.blogspot.mvnblogbuild.kalah.exceptions.NotFoundException;
import com.blogspot.mvnblogbuild.kalah.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static com.blogspot.mvnblogbuild.kalah.core.KalahCore.INITIAL_STONES_IN_PIT;
import static java.lang.String.format;

@Service
public class GameService {

    @Autowired
    private DTOConverterUtil dtoConverterUtil;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private KalahCore kalahCore;

    @Transactional
    public GameDTO createGame() {
        Game game = new Game();
        game.setNorthPlayer(initializePlayerGameSession());
        game.setSouthPlayer(initializePlayerGameSession());
        game.setPlayerMove(PlayerMove.NONE);
        Game savedGame = gameRepository.save(game);
        return dtoConverterUtil.convertToGame(savedGame);
    }

    @Transactional
    public GameStateDTO makeAMove(Long gameId, Integer pitId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException(format("Game with ID %d is not found", gameId)));

        // Make a move and update the game state
        Game updatedGame = kalahCore.makeAMove(game, pitId);

        gameRepository.save(updatedGame);
        return dtoConverterUtil.convertToGameState(updatedGame);
    }

    private PlayerGameSession initializePlayerGameSession() {
        PlayerGameSession playerGameSession = new PlayerGameSession();
        playerGameSession.setKalah(0);
        playerGameSession.setPits(IntStream.range(1, 7)
                .map(i -> INITIAL_STONES_IN_PIT)
                .boxed()
                .toArray(Integer[]::new));
        return playerGameSession;
    }

}
