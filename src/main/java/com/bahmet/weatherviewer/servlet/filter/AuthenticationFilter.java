package com.bahmet.weatherviewer.servlet.filter;

import com.bahmet.weatherviewer.dao.SessionDAO;
import com.bahmet.weatherviewer.model.Session;
import com.bahmet.weatherviewer.servlet.BaseServlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebFilter(urlPatterns = {"/sign-in", "/sign-up"})
public class AuthenticationFilter implements Filter {
    private SessionDAO sessionDAO;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        sessionDAO = (SessionDAO) filterConfig.getServletContext().getAttribute("sessionDAO");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Cookie[] cookies = req.getCookies();

        Optional<Cookie> cookie = BaseServlet.findCookieByName(cookies, "session_id");

        if (cookie.isPresent()) {
            UUID sessionId = UUID.fromString(cookie.get().getValue());
            Optional<Session> session = sessionDAO.findById(sessionId);
            if (session.isPresent() && !BaseServlet.isSessionExpired(session.get())) {
                resp.sendRedirect("home");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
