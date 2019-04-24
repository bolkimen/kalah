package com.blogspot.mvnblogbuild.kalah.controller;

import com.blogspot.mvnblogbuild.kalah.KalahApplication;
import com.blogspot.mvnblogbuild.kalah.dto.GameDTO;
import com.blogspot.mvnblogbuild.kalah.service.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = KalahApplication.class)
@AutoConfigureMockMvc
public class GameControllerIntegrationTest {

    private static final Long NON_EXISTS_GAME_ID = 123456789l;

    @Value("${game.base.uri}")
    private String baseUrl;

    @Autowired
    private GameService gameService;

    private GameDTO existingGame;

    @Autowired
    private MockMvc mvc;

    @Before
    public void init() {
        existingGame = gameService.createGame();
    }

    @Test
    public void testCreateGame() throws Exception {

        mvc.perform(post("/games/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.uri", is("http://localhost:8090/games/2")));
    }

    @Test
    public void testMakeAMoveWithNonExistsGame() throws Exception {

        mvc.perform(put("/games/" + NON_EXISTS_GAME_ID + "/pits/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + 123 + uri;
    }

}
