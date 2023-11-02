package com.bahmet.weatherviewer.exception;

public class GeocodingApiCallException extends RuntimeException {
    public GeocodingApiCallException(String message) {
        super(message);
    }
}
