package com.bahmet.weatherviewer.exception.openweathermap;

public class OpenWeatherMapApiNotFoundException extends RuntimeException {
    public OpenWeatherMapApiNotFoundException(String message) {
        super(message);
    }
}
