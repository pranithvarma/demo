package com.practice.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.practice.demo.entity.WeatherDescription;
import com.practice.demo.entity.WeatherResponse;

@Service
public class WeatherService {
	private final RestClient restClient;
    private final String apiKey;
    private final String weatherApiUrl;

    public WeatherService(RestClient restClient, 
                          @Value("${weather.api.key}") String apiKey,
                          @Value("${weather.api.url}") String weatherApiUrl) {
        this.restClient = restClient;
        this.apiKey = apiKey;
        this.weatherApiUrl = weatherApiUrl;
    }

    public List<WeatherDescription> getWeather(String city) {
        String url = String.format("%s?q=%s&appid=%s", weatherApiUrl, city, apiKey);

        WeatherResponse response = restClient.get()
                .uri(url)
                .retrieve()
                .body(WeatherResponse.class);

        // Extract and return the weather list
        return response.weather();
    }

}
