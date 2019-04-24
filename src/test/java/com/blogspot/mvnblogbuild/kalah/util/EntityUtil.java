package com.blogspot.mvnblogbuild.kalah.util;

import com.blogspot.mvnblogbuild.kalah.core.PlayerMove;
import com.blogspot.mvnblogbuild.kalah.domain.Game;
import com.blogspot.mvnblogbuild.kalah.domain.PlayerGameSession;

public class EntityUtil {

    public static Game createNewGame() {
        PlayerGameSession northPlayer = createPlayerGameSession(0, new Integer[] {6, 6, 6, 6, 6, 6});
        PlayerGameSession southPlayer = createPlayerGameSession(0, new Integer[] {6, 6, 6, 6, 6, 6});
        return createGame(northPlayer, southPlayer, PlayerMove.NONE);
    }

    public static Game createGame(PlayerGameSession northPlayer, PlayerGameSession southPlayer, PlayerMove playerMove) {
        Game game = new Game();
        game.setNorthPlayer(northPlayer);
        game.setSouthPlayer(southPlayer);
        game.setPlayerMove(playerMove);
        return game;
    }

    public static PlayerGameSession createPlayerGameSession(Integer kalah, Integer[] pits) {
        PlayerGameSession playerGameSession = new PlayerGameSession();
        playerGameSession.setKalah(kalah);
        playerGameSession.setPits(pits);
        return playerGameSession;
    }

}
