package com.blogspot.mvnblogbuild.kalah.repository;

import com.blogspot.mvnblogbuild.kalah.domain.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    Game findByGameId(Integer gameId);

}
