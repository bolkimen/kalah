package com.blogspot.mvnblogbuild.kalah.core;

import com.blogspot.mvnblogbuild.kalah.domain.Game;
import org.springframework.stereotype.Component;

@Component
public class KalahCore {

    public static final Integer INITIAL_STONES_IN_PIT = 6;

    public Game makeAMove(Game game, Integer pitId) {
        return game;
    }

}
