package com.bahmet.weatherviewer.servlet.authentication;

import com.bahmet.weatherviewer.dao.SessionDAO;
import com.bahmet.weatherviewer.dao.UserDAO;
import com.bahmet.weatherviewer.exception.UserNotFoundException;
import com.bahmet.weatherviewer.model.Session;
import com.bahmet.weatherviewer.model.User;
import com.bahmet.weatherviewer.service.AuthService;
import com.bahmet.weatherviewer.servlet.BaseServlet;
import com.bahmet.weatherviewer.util.ValidatorUtil;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet("/sign-in")
public class SignInServlet extends BaseServlet {
    private final UserDAO userDAO = new UserDAO();
    private final SessionDAO sessionDAO = new SessionDAO();

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        templateEngine.process("log_in", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        ValidatorUtil.validateAuthParameters(username, password);

        User user = authService.signIn(username, password);

        Session session = new Session(UUID.randomUUID(), user, LocalDateTime.now().plusDays(7));
        sessionDAO.save(session);

        Cookie cookie = new Cookie("session_id", session.getId().toString());
        resp.addCookie(cookie);

        resp.sendRedirect("/");
    }
}
