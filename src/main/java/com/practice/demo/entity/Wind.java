package com.practice.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Wind(@JsonProperty("speed") double speed,
        @JsonProperty("deg") int deg) {

}
