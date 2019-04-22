package com.blogspot.mvnblogbuild.kalah.controller;

import com.blogspot.mvnblogbuild.kalah.dto.GameDTO;
import com.blogspot.mvnblogbuild.kalah.dto.GameStateDTO;
import com.blogspot.mvnblogbuild.kalah.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/")
    public String mainPage() {
        return "index";
    }

    @RequestMapping(
            value = "/games",
            method = RequestMethod.POST,
            produces = APPLICATION_JSON_VALUE)
    public GameDTO createGame() {
        return gameService.createGame();
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/games/{gameId}/pits/{pitId}",
            produces = APPLICATION_JSON_VALUE)
    public GameStateDTO makeAMove(@PathVariable("gameId") Long gameId,
                                  @PathVariable("pitId") Integer pitId) {
        return gameService.makeAMove(gameId, pitId);
    }

}
