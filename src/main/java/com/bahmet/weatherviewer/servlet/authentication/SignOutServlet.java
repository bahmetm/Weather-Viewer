package com.bahmet.weatherviewer.servlet.authentication;

import com.bahmet.weatherviewer.dao.SessionDAO;
import com.bahmet.weatherviewer.exception.CookieNotFoundException;
import com.bahmet.weatherviewer.exception.SessionExpiredException;
import com.bahmet.weatherviewer.exception.SessionNotFoundException;
import com.bahmet.weatherviewer.model.Session;
import com.bahmet.weatherviewer.servlet.BaseServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/sign-out")
public class SignOutServlet extends BaseServlet {
    private SessionDAO sessionDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sessionDAO = (SessionDAO) config.getServletContext().getAttribute("sessionDAO");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();

        Cookie cookie = findCookieByName(cookies, "session_id").orElseThrow(() -> new CookieNotFoundException("Cookie with session id not found."));

        UUID sessionId = UUID.fromString(cookie.getValue());

        Session session = sessionDAO.findById(sessionId).orElseThrow(() -> new SessionNotFoundException("Session with id " + sessionId + " not found."));

        if (isSessionExpired(session)) {
            throw new SessionExpiredException("Session with id " + sessionId + " is expired.");
        }

        sessionDAO.delete(session);

        Cookie newCookie = new Cookie("session_id", "");

        newCookie.setMaxAge(0);
        resp.addCookie(newCookie);

        resp.sendRedirect("/sign-in");
    }
}
