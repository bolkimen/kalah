package com.blogspot.mvnblogbuild.kalah.controller;

import com.blogspot.mvnblogbuild.kalah.KalahApplication;
import com.blogspot.mvnblogbuild.kalah.core.PlayerMove;
import com.blogspot.mvnblogbuild.kalah.domain.Game;
import com.blogspot.mvnblogbuild.kalah.repository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.blogspot.mvnblogbuild.kalah.util.EntityUtil.createNewGame;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = KalahApplication.class)
@AutoConfigureMockMvc
public class GameControllerIntegrationTest {

    private static final Integer NEXT_NEW_GAME_ID = 1;
    private static final Long NON_EXISTS_GAME_ID = 0l;

    @Value("${game.base.uri}")
    private String baseUrl;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testCreateGame() throws Exception {
        int newGameId = (int)gameRepository.count() + 1;

        mvc.perform(post("/games/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(newGameId)))
                .andExpect(jsonPath("$.uri", is(baseUrl + newGameId)));
    }

    @Test
    public void testMakeAMoveWithNonExistsGame() throws Exception {

        mvc.perform(put("/games/" + NON_EXISTS_GAME_ID + "/pits/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testMakeAMoveWithWrongPitId() throws Exception {
        Game game = createNewGame();
        game = gameRepository.save(game);

        mvc.perform(put("/games/" + game.getId() + "/pits/7")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testMakeAMoveWithWrongPlayerMove() throws Exception {
        Game game = createNewGame();
        game.setPlayerMove(PlayerMove.NORTH);
        game = gameRepository.save(game);

        mvc.perform(put("/games/" + game.getId() + "/pits/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

}
