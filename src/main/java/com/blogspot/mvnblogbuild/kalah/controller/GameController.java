package com.blogspot.mvnblogbuild.kalah.controller;

import com.blogspot.mvnblogbuild.kalah.dto.GameDTO;
import com.blogspot.mvnblogbuild.kalah.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping(value = "/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping
    public String mainPage() {
        return "index";
    }

    @RequestMapping(
            method = RequestMethod.POST,
            produces = APPLICATION_JSON_VALUE)
    public GameDTO createGame() {
        return "index";
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{gameId}/pits/{pitId}",
            produces = APPLICATION_JSON_VALUE)
    public String makeAMove(@PathVariable("gameId") Integer gameId,
                            @PathVariable("pitId") Integer pitId) {
        return "index";
    }

}
