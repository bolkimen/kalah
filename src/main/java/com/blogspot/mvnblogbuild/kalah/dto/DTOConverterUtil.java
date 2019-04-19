package com.blogspot.mvnblogbuild.kalah.dto;

import com.blogspot.mvnblogbuild.kalah.domain.Game;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
                .id(game.getId())
                .uri(getGameURI(game))
                .build();
    }

    public GameStateDTO convertToGameState(Game game) {
        return GameStateDTO.builder()
                .id(game.getId())
                .uri(getGameURI(game))
                .status(getGameStatus(game))
                .build();
    }

    private String getGameURI(Game game) {
        return baseURI + game.getId();
    }

    private Map<String, String> getGameStatus(Game game) {
        return null;
    }

}
