package com.bahmet.weatherviewer.exception;

import javax.servlet.ServletException;

public class SessionNotFoundException extends ServletException {
    public SessionNotFoundException(String message) {
        super(message);
    }
}
