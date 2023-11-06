package com.bahmet.weatherviewer.service;


import com.bahmet.weatherviewer.dto.GeocodingApiResponse;
import com.bahmet.weatherviewer.exception.GeocodingApiCallException;
import com.bahmet.weatherviewer.util.OpenWeatherMapUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class GeoCodingApiService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GeoCodingApiService(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public List<GeocodingApiResponse> getGeoDataByLocationName(String locationName) {
        try {
            URI uri = getGeoCodingUri(locationName);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            CompletableFuture<HttpResponse<String>> futureResponse = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .orTimeout(10, TimeUnit.SECONDS);

            HttpResponse<String> response = futureResponse.get();

            OpenWeatherMapUtil.checkStatusCode(response.statusCode());

            String responseJson = response.body();

            return objectMapper.readValue(responseJson, new TypeReference<List<GeocodingApiResponse>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeocodingApiCallException("Error while calling geocoding api.");
        }
    }

    private URI getGeoCodingUri(String locationName) {
        String uri = OpenWeatherMapUtil.getBaseUrl() +
                OpenWeatherMapUtil.getGeocodingApiUrl() +
                "?q=" + locationName +
                "&limit=" + OpenWeatherMapUtil.getLimit() +
                "&appid=" + OpenWeatherMapUtil.getApiKey();

        return URI.create(uri);
    }
}
