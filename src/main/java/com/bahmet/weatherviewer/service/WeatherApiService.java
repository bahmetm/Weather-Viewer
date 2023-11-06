package com.bahmet.weatherviewer.service;

import com.bahmet.weatherviewer.dto.WeatherApiResponse;
import com.bahmet.weatherviewer.exception.WeatherApiCallException;
import com.bahmet.weatherviewer.model.Location;
import com.bahmet.weatherviewer.util.OpenWeatherMapUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class WeatherApiService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public WeatherApiService(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public WeatherApiResponse getWeatherByLocation(Location location) {
        try {
            URI uri = getWeatherUri(location);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            CompletableFuture<HttpResponse<String>> futureResponse = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .orTimeout(10, TimeUnit.SECONDS);

            HttpResponse<String> response = futureResponse.get();

            OpenWeatherMapUtil.checkStatusCode(response.statusCode());

            String responseJson = response.body();

            return objectMapper.readValue(responseJson, WeatherApiResponse.class);
        } catch (Exception e) {
            throw new WeatherApiCallException("Error while calling weather api.");
        }
    }

    private URI getWeatherUri(Location location) {
        String uri = OpenWeatherMapUtil.getBaseUrl() +
                OpenWeatherMapUtil.getCurrentWeatherApiUrl() +
                "?lat=" + location.getLatitude() +
                "&lon=" + location.getLongitude() +
                "&appid=" + OpenWeatherMapUtil.getApiKey() +
                "&units=" + OpenWeatherMapUtil.getUnits();

        return URI.create(uri);
    }
}
