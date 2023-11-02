package com.bahmet.weatherviewer.exception.openweathermap;

public class OpenWeatherMapApiTooManyRequestsException extends RuntimeException {
    public OpenWeatherMapApiTooManyRequestsException(String message) {
        super(message);
    }
}
