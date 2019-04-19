package com.blogspot.mvnblogbuild.kalah.dto;

import com.blogspot.mvnblogbuild.kalah.domain.Game;
import com.blogspot.mvnblogbuild.kalah.domain.PlayerGameSession;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DTOConverterUtilTest {

    private final static Long GAME_ID = 1234l;
    private final static String BASE_URI = "URI/";

    private DTOConverterUtil dtoConverterUtil;

    @Before
    public void init() {
        dtoConverterUtil = new DTOConverterUtil();
        dtoConverterUtil.setBaseURI(BASE_URI);
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
    }

    private Game createGame() {
        Game game = new Game();
        game.setId(GAME_ID);
        game.setFirstPlayer(createPlayerGameSession(1l, 11, new Integer[] {1,2,3,4,5,6}));
        game.setSecondPlayer(createPlayerGameSession(2l, 22, new Integer[] {21,22,23,24,25,26}));
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
