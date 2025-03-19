package com.practice.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Clouds(        @JsonProperty("all") int all
) {

}
