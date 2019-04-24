package com.blogspot.mvnblogbuild.kalah.controller;

import com.blogspot.mvnblogbuild.kalah.KalahApplication;
import com.blogspot.mvnblogbuild.kalah.dto.GameDTO;
import com.blogspot.mvnblogbuild.kalah.service.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = KalahApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerIntegrationTest {

    private static final Long NON_EXISTS_GAME_ID = 123456789l;

    @Value("${game.base.uri}")
    private String baseUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private GameService gameService;

    private TestRestTemplate restTemplate;
    private HttpHeaders headers;

    private GameDTO existingGame;

    @Before
    public void init() {
        restTemplate = new TestRestTemplate();
        headers = new HttpHeaders();

        existingGame = gameService.createGame();
    }

    @Test
    public void testCreateGame() {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<GameDTO> response = restTemplate.exchange(
                createURLWithPort("/games/"),
                HttpMethod.POST, entity, GameDTO.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        GameDTO game = response.getBody();
        assertEquals(game.getUri(), baseUrl + game.getId());
    }

    @Test
    public void testMakeAMoveWithNonExistsGame() {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("/games/" + NON_EXISTS_GAME_ID + "/pits/1"),
                HttpMethod.PUT, entity, Object.class);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
