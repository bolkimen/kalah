package com.blogspot.mvnblogbuild.kalah.domain;

import com.blogspot.mvnblogbuild.kalah.core.PlayerMove;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "north_player_id")
    private PlayerGameSession northPlayer;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "south_player_id")
    private PlayerGameSession southPlayer;

    @Enumerated(EnumType.STRING)
    private PlayerMove playerMove;

}
