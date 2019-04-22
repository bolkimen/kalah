package com.blogspot.mvnblogbuild.kalah.dto;

import com.blogspot.mvnblogbuild.kalah.domain.Game;
import com.blogspot.mvnblogbuild.kalah.domain.PlayerGameSession;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class DTOConverterUtilTest {

    private final static Long GAME_ID = 1234l;
    private final static String BASE_URI = "URI/";

    private DTOConverterUtil dtoConverterUtil;
    private Map<String, String> gameStatus;

    @Before
    public void init() {
        dtoConverterUtil = new DTOConverterUtil();
        dtoConverterUtil.setBaseURI(BASE_URI);

        Random rand = new Random();
        gameStatus = new LinkedHashMap<>();
        IntStream.range(1, 15)
                .forEach(i -> gameStatus.put(String.valueOf(i), String.valueOf(rand.nextInt())));
    }

    @Test
    public void testConvertToGame() {
        Game game = createGame();
        GameDTO gameDTO = dtoConverterUtil.convertToGame(game);
        assertEquals(gameDTO.getId(), GAME_ID);
        assertEquals(gameDTO.getUri(), BASE_URI + GAME_ID);
    }

    @Test
    public void testConvertToGameState() {
        Game game = createGame();
        GameStateDTO gameStateDTO = dtoConverterUtil.convertToGameState(game);
        assertEquals(gameStateDTO.getId(), GAME_ID);
        assertEquals(gameStateDTO.getUri(), BASE_URI + GAME_ID);
        assertEquals(gameStateDTO.getStatus(), gameStatus);
    }

    private Game createGame() {
        Game game = new Game();
        game.setId(GAME_ID);
        IntStream.range(1, 14)
                .map(i -> Integer.valueOf(gameStatus.get(String.valueOf(i))))
                .boxed()
                .toArray(Integer[]::new);
        game.setNorthPlayer(createPlayerGameSession(1l,
                Integer.valueOf(gameStatus.get("7")),
                IntStream.range(1, 7)
                        .map(i -> Integer.valueOf(gameStatus.get(String.valueOf(i))))
                        .boxed()
                        .toArray(Integer[]::new)));
        game.setSouthPlayer(createPlayerGameSession(2l,
                Integer.valueOf(gameStatus.get("14")),
                IntStream.range(8, 14)
                        .map(i -> Integer.valueOf(gameStatus.get(String.valueOf(i))))
                        .boxed()
                        .toArray(Integer[]::new)));
        return game;
    }

    private PlayerGameSession createPlayerGameSession(Long playerId, Integer kalah, Integer[] pits) {
        PlayerGameSession playerGameSession = new PlayerGameSession();
        playerGameSession.setId(playerId);
        playerGameSession.setKalah(kalah);
        playerGameSession.setPits(pits);
        return playerGameSession;
    }

}
