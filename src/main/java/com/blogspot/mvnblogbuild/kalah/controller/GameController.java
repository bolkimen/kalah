package com.blogspot.mvnblogbuild.kalah.controller;

import com.blogspot.mvnblogbuild.kalah.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @RequestMapping(value = "/games")
    public String mainPage() {
        return "index";
    }

}
