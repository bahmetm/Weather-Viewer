package com.bahmet.weatherviewer.exception;

import javax.persistence.EntityExistsException;

public class UserExistsException extends EntityExistsException {
    public UserExistsException(String message) {
        super(message);
    }
}
