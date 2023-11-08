package com.bahmet.weatherviewer.service;

import com.bahmet.weatherviewer.dao.SessionDAO;
import com.bahmet.weatherviewer.dao.UserDAO;
import com.bahmet.weatherviewer.exception.PasswordNotMatchException;
import com.bahmet.weatherviewer.exception.UserExistsException;
import com.bahmet.weatherviewer.exception.UserNotFoundException;
import com.bahmet.weatherviewer.model.User;
import com.bahmet.weatherviewer.util.ValidatorUtil;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {
    private final UserDAO userDAO;
    private final SessionDAO sessionDAO;

    public AuthService(UserDAO userDAO, SessionDAO sessionDAO) {
        this.userDAO = userDAO;
        this.sessionDAO = sessionDAO;
    }

    public void signUp(String username, String password) {
        userDAO.findByUsername(username).ifPresent(user -> {
            throw new UserExistsException("User with this username already exist.");
        });

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(username, hashedPassword);
        userDAO.save(user);
    }

    public User signIn(String username, String password) throws UserNotFoundException {
        User user = userDAO.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found."));

        if (!ValidatorUtil.validatePassword(password, user.getPassword())) {
            throw new PasswordNotMatchException("Wrong password.");
        }

        return user;
    }
}
