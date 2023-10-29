package com.bahmet.weatherviewer.exception;

public class UserNotFoundException extends DatabaseException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
