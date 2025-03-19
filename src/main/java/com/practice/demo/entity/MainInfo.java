package com.practice.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MainInfo( @JsonProperty("temp") double temp,
        @JsonProperty("pressure") int pressure,
        @JsonProperty("humidity") int humidity,
        @JsonProperty("temp_min") double tempMin,
        @JsonProperty("temp_max") double tempMax) {

}
