package com.blogspot.mvnblogbuild.kalah.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class GameStateDTO {
    private Long id;
    private String uri;
    private Map<String, String> status;
}
