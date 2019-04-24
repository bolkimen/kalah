package com.blogspot.mvnblogbuild.kalah.core;

import com.blogspot.mvnblogbuild.kalah.domain.Game;
import org.junit.Test;

import static com.blogspot.mvnblogbuild.kalah.util.EntityUtil.createNewGame;

public class KalahCoreTest {

    private KalahCore kalahCore = new KalahCore();

    @Test(expected = IllegalArgumentException.class)
    public void testMakeAMoveWithWrongPitId() throws Exception {
        Game game = createNewGame();
        kalahCore.makeAMove(game, 7);
    }

    @Test(expected = IllegalStateException.class)
    public void testMakeAMoveWithWrongPlayerMove() throws Exception {
        Game game = createNewGame();
        game.setPlayerMove(PlayerMove.NORTH);

        kalahCore.makeAMove(game, 10);
    }

    @Test
    public void testMakeAMove() {

    }

}
