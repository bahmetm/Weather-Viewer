package com.bahmet.weatherviewer.exception;

import javax.servlet.ServletException;

public class CookieNotFoundException extends ServletException {
    public CookieNotFoundException(String message) {
        super(message);
    }
}
