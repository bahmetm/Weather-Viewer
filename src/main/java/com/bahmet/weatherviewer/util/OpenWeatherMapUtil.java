package com.bahmet.weatherviewer.util;

import com.bahmet.weatherviewer.exception.LoadingPropertiesFailedException;
import com.bahmet.weatherviewer.exception.openweathermap.OpenWeatherMapApiBadRequestException;
import com.bahmet.weatherviewer.exception.openweathermap.OpenWeatherMapApiInternalErrorException;
import com.bahmet.weatherviewer.exception.openweathermap.OpenWeatherMapApiNotFoundException;
import com.bahmet.weatherviewer.exception.openweathermap.OpenWeatherMapApiTooManyRequestsException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class OpenWeatherMapUtil {
    private static String baseUrl;
    private static String apiKey;
    private static String geocodingApiUrl;
    private static String currentWeatherApiUrl;
    private static String limit;

    private static String units;

    static {
        loadProperties();
    }

    public static void loadProperties() {
        try (InputStream inputStream = OpenWeatherMapUtil.class.getClassLoader().getResourceAsStream("openweathermap.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);

            baseUrl = properties.getProperty("BASE_URL");
            apiKey = properties.getProperty("API_KEY");
            geocodingApiUrl = properties.getProperty("GEOCODING_API_URL");
            currentWeatherApiUrl = properties.getProperty("CURRENT_WEATHER_API_URL");
            limit = properties.getProperty("LIMIT");
            units = properties.getProperty("UNITS");
        } catch (IOException e) {
            throw new LoadingPropertiesFailedException("Failed to load properties.");
        }
    }

    public static void checkStatusCode(int statusCode) {
        if (statusCode != 200) {
            switch (statusCode) {
                case 400:
                    throw new OpenWeatherMapApiBadRequestException("Nothing to geocode.");
                case 401:
                    throw new OpenWeatherMapApiBadRequestException("Invalid API key.");
                case 404:
                    throw new OpenWeatherMapApiNotFoundException("Nothing found.");
                case 429:
                    throw new OpenWeatherMapApiTooManyRequestsException("Too many requests.");
                default:
                    throw new OpenWeatherMapApiInternalErrorException("OpenWeatherMap API internal error.");
            }
        }
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getGeocodingApiUrl() {
        return geocodingApiUrl;
    }

    public static String getCurrentWeatherApiUrl() {
        return currentWeatherApiUrl;
    }

    public static String getLimit() {
        return limit;
    }

    public static String getUnits() {
        return units;
    }
}
