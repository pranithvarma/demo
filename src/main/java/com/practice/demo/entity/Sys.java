package com.practice.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Sys(@JsonProperty("type") int type,
        @JsonProperty("id") int id,
        @JsonProperty("message") double message,
        @JsonProperty("country") String country,
        @JsonProperty("sunrise") long sunrise,
        @JsonProperty("sunset") long sunset) {

}
