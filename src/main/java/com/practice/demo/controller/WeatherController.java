package com.practice.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.demo.entity.WeatherDescription;
import com.practice.demo.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherController {
	private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public List<WeatherDescription> getWeather(@RequestParam String city) {
        return weatherService.getWeather(city);
    }
}
