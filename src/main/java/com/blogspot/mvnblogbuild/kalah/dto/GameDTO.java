package com.blogspot.mvnblogbuild.kalah.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameDTO {
    private Long id;
    private String uri;
}
