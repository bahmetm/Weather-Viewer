package com.bahmet.weatherviewer.exception;

public class WeatherApiCallException extends RuntimeException {
    public WeatherApiCallException(String message) {
        super(message);
    }
}
