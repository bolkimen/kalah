package com.blogspot.mvnblogbuild.kalah.service;

import com.blogspot.mvnblogbuild.kalah.domain.Game;
import com.blogspot.mvnblogbuild.kalah.domain.PlayerGameSession;
import com.blogspot.mvnblogbuild.kalah.dto.DTOConverterUtil;
import com.blogspot.mvnblogbuild.kalah.dto.GameDTO;
import com.blogspot.mvnblogbuild.kalah.dto.GameStateDTO;
import com.blogspot.mvnblogbuild.kalah.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

import static com.blogspot.mvnblogbuild.kalah.core.KalahCore.INITIAL_STONES_IN_PIT;

@Service
public class GameService {

    @Autowired
    private DTOConverterUtil dtoConverterUtil;

    @Autowired
    private GameRepository gameRepository;

    @Transactional
    public GameDTO createGame() {
        Game game = new Game();
        game.setNorthPlayer(initializePlayerGameSession());
        game.setSouthPlayer(initializePlayerGameSession());
        Game savedGame = gameRepository.save(game);
        return dtoConverterUtil.convertToGame(savedGame);
    }

    public GameStateDTO makeAMove(Long gameId, Integer pitId) {
        Optional<Game> game = gameRepository.findById(gameId);
        // TODO implement game logic
        return dtoConverterUtil.convertToGameState(game.get());
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
