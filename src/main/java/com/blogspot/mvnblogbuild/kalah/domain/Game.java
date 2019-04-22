package com.blogspot.mvnblogbuild.kalah.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(
        name="game",
        indexes = { @Index(name = "GAME_INDX_0", columnList = "gameId") }
        )
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique=true)
    private Integer gameId;

    @OneToOne
    @JoinColumn(name = "north_player_id")
    private PlayerGameSession northPlayer;

    @OneToOne
    @JoinColumn(name = "south_player_id")
    private PlayerGameSession southPlayer;

}
