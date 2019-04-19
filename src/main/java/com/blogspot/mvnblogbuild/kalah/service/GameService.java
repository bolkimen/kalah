package com.blogspot.mvnblogbuild.kalah.service;

import com.blogspot.mvnblogbuild.kalah.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

}
