package com.blogspot.mvnblogbuild.kalah.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Integer gameId;

    @OneToOne
    @JoinColumn(name = "first_player_id")
    private PlayerGameSession firstPlayer;

    @OneToOne
    @JoinColumn(name = "second_player_id")
    private PlayerGameSession secondPlayer;

}
