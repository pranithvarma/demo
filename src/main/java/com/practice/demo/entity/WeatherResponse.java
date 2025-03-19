package com.practice.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherResponse(@JsonProperty("coord") Coord coord,
        @JsonProperty("weather") List<WeatherDescription> weather,
        @JsonProperty("base") String base,
        @JsonProperty("main") MainInfo main,
        @JsonProperty("visibility") int visibility,
        @JsonProperty("wind") Wind wind,
        @JsonProperty("clouds") Clouds clouds,
        @JsonProperty("dt") long dt,
        @JsonProperty("sys") Sys sys,
        @JsonProperty("id") int id,
        @JsonProperty("name") String name,
        @JsonProperty("cod") int cod) {

}
