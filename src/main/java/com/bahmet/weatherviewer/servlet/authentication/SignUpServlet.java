package com.bahmet.weatherviewer.servlet.authentication;

import com.bahmet.weatherviewer.dao.SessionDAO;
import com.bahmet.weatherviewer.dao.UserDAO;
import com.bahmet.weatherviewer.model.User;
import com.bahmet.weatherviewer.servlet.BaseServlet;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;

@WebServlet("/sign-up")
public class SignUpServlet extends BaseServlet {
    private final UserDAO userDAO = new UserDAO();
    private final SessionDAO sessionDAO = new SessionDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        templateEngine.process("sign_up", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.isEmpty()) {
            throw new InvalidParameterException("Username cannot be empty.");
        }

        if (password == null || password.isEmpty()) {
            throw new InvalidParameterException("Password cannot be empty.");
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(username, hashedPassword);
        userDAO.save(user);

        resp.sendRedirect("/sign-in");
    }
}
