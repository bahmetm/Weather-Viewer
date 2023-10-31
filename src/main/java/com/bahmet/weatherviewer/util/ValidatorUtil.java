package com.bahmet.weatherviewer.util;

import org.mindrot.jbcrypt.BCrypt;

import java.security.InvalidParameterException;

public class ValidatorUtil {
    public static void validateAuthParameters(String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new InvalidParameterException("Username cannot be empty.");
        }

        if (password == null || password.isEmpty()) {
            throw new InvalidParameterException("Password cannot be empty.");
        }
    }

    public static boolean validatePassword(String hashedPassword, String password) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
