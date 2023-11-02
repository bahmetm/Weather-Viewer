package com.bahmet.weatherviewer.exception.openweathermap;

public class OpenWeatherMapApiInvalidApiKeyException extends RuntimeException {
    public OpenWeatherMapApiInvalidApiKeyException(String message) {
        super(message);
    }
}
