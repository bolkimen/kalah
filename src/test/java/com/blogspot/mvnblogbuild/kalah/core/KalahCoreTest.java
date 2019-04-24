package com.blogspot.mvnblogbuild.kalah.core;

import com.blogspot.mvnblogbuild.kalah.domain.Game;
import com.blogspot.mvnblogbuild.kalah.domain.PlayerGameSession;
import org.junit.Test;

import static com.blogspot.mvnblogbuild.kalah.util.EntityUtil.createGame;
import static com.blogspot.mvnblogbuild.kalah.util.EntityUtil.createNewGame;
import static com.blogspot.mvnblogbuild.kalah.util.EntityUtil.createPlayerGameSession;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class KalahCoreTest {

    private KalahCore kalahCore = new KalahCore();

    @Test(expected = IllegalArgumentException.class)
    public void testMakeAMoveWithWrongPitId() {
        Game game = createNewGame();
        kalahCore.makeAMove(game, 7);
    }

    @Test(expected = IllegalStateException.class)
    public void testMakeAMoveWithWrongPlayerMove() {
        Game game = createNewGame();
        game.setPlayerMove(PlayerMove.NORTH);

        kalahCore.makeAMove(game, 10);
    }

    // Game already finished
    @Test(expected = IllegalStateException.class)
    public void testMakeAMoveWithFinishedGame() {
        Game game = createNewGame();
        game.setPlayerMove(PlayerMove.FINISHED);

        kalahCore.makeAMove(game, 1);
    }

    // We try to pick stones from empty pit
    @Test(expected = IllegalStateException.class)
    public void testMakeAMoveWithEmptyPit() {
        PlayerGameSession northPlayer = createPlayerGameSession(0, new Integer[] {0, 6, 6, 6, 6, 6});
        PlayerGameSession southPlayer = createPlayerGameSession(0, new Integer[] {6, 6, 6, 6, 6, 6});
        Game game = createGame(northPlayer, southPlayer, PlayerMove.NONE);

        kalahCore.makeAMove(game, 1);
    }

    @Test
    public void testMakeAMoveSecondTurnForNorthPlayer() {
        PlayerGameSession northPlayer = createPlayerGameSession(0, new Integer[] {6, 6, 6, 6, 6, 6});
        PlayerGameSession southPlayer = createPlayerGameSession(0, new Integer[] {6, 6, 6, 6, 6, 6});
        Game game = createGame(northPlayer, southPlayer, PlayerMove.NONE);

        Game updatedGame = kalahCore.makeAMove(game, 1);
        assertEquals(updatedGame.getPlayerMove(), PlayerMove.NORTH);
        assertEquals(updatedGame.getNorthPlayer().getKalah(), Integer.valueOf(1));
        assertArrayEquals(updatedGame.getNorthPlayer().getPits(), new Integer[] {0, 7, 7, 7, 7, 7});
        assertEquals(updatedGame.getSouthPlayer().getKalah(), Integer.valueOf(0));
        assertArrayEquals(updatedGame.getSouthPlayer().getPits(), new Integer[] {6, 6, 6, 6, 6, 6});
    }

    @Test
    public void testMakeAMoveSwitchTurnToSouthPlayer() {
        PlayerGameSession northPlayer = createPlayerGameSession(0, new Integer[] {6, 6, 6, 6, 6, 6});
        PlayerGameSession southPlayer = createPlayerGameSession(0, new Integer[] {6, 6, 6, 6, 6, 6});
        Game game = createGame(northPlayer, southPlayer, PlayerMove.NONE);

        Game updatedGame = kalahCore.makeAMove(game, 2);
        assertEquals(updatedGame.getPlayerMove(), PlayerMove.SOUTH);
        assertEquals(updatedGame.getNorthPlayer().getKalah(), Integer.valueOf(1));
        assertArrayEquals(updatedGame.getNorthPlayer().getPits(), new Integer[] {6, 0, 7, 7, 7, 7});
        assertEquals(updatedGame.getSouthPlayer().getKalah(), Integer.valueOf(0));
        assertArrayEquals(updatedGame.getSouthPlayer().getPits(), new Integer[] {7, 6, 6, 6, 6, 6});
    }

    @Test
    public void testMakeAMoveGrabOppositeStones() {
        PlayerGameSession northPlayer = createPlayerGameSession(0, new Integer[] {6, 6, 6, 1, 0, 6});
        PlayerGameSession southPlayer = createPlayerGameSession(0, new Integer[] {6, 6, 6, 6, 6, 6});
        Game game = createGame(northPlayer, southPlayer, PlayerMove.NONE);

        Game updatedGame = kalahCore.makeAMove(game, 4);
        assertEquals(updatedGame.getPlayerMove(), PlayerMove.SOUTH);
        assertEquals(updatedGame.getNorthPlayer().getKalah(), Integer.valueOf(7));
        assertArrayEquals(updatedGame.getNorthPlayer().getPits(), new Integer[] {6, 6, 6, 0, 0, 6});
        assertEquals(updatedGame.getSouthPlayer().getKalah(), Integer.valueOf(0));
        assertArrayEquals(updatedGame.getSouthPlayer().getPits(), new Integer[] {6, 0, 6, 6, 6, 6});
    }

}
