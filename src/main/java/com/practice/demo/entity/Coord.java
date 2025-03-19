package com.practice.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Coord(@JsonProperty("lon") double lon,
        @JsonProperty("lat") double lat) {

}
