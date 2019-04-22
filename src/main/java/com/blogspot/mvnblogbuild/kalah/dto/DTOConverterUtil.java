package com.blogspot.mvnblogbuild.kalah.dto;

import com.blogspot.mvnblogbuild.kalah.domain.Game;
import com.blogspot.mvnblogbuild.kalah.domain.PlayerGameSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class DTOConverterUtil {

    @Value("${game.base.uri:http://localhost:8090/games/}")
    private String baseURI;

    public void setBaseURI(String baseURI) {
        this.baseURI = baseURI;
    }

    public GameDTO convertToGame(Game game) {
        return GameDTO.builder()
                .id(game.getGameId())
                .uri(getGameURI(game))
                .build();
    }

    public GameStateDTO convertToGameState(Game game) {
        return GameStateDTO.builder()
                .id(game.getGameId())
                .uri(getGameURI(game))
                .status(getGameStatus(game))
                .build();
    }

    private String getGameURI(Game game) {
        return baseURI + game.getId();
    }

    private Map<String, String> getGameStatus(Game game) {
        Map<String, String> gameStatus = new LinkedHashMap<>();
        fillPlayerGameStatus(gameStatus, game.getNorthPlayer(), 1);
        fillPlayerGameStatus(gameStatus, game.getSouthPlayer(), 8);
        return gameStatus;
    }

    private void fillPlayerGameStatus(Map<String, String> gameStatus, PlayerGameSession firstPlayer, Integer startsFrom) {
        for (int i = 0; i < firstPlayer.getPits().length; i++) {
            gameStatus.put(String.valueOf(i + startsFrom), String.valueOf(firstPlayer.getPits()[i]));
        }
        gameStatus.put(String.valueOf(firstPlayer.getPits().length + startsFrom), String.valueOf(firstPlayer.getKalah()));
    }

}
