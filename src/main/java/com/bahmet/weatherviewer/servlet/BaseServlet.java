package com.bahmet.weatherviewer.servlet;

import com.bahmet.weatherviewer.model.Session;
import com.bahmet.weatherviewer.util.ThymeleafUtil;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

public class BaseServlet extends HttpServlet {
    protected ITemplateEngine templateEngine;
    protected WebContext webContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        templateEngine = (ITemplateEngine) config.getServletContext().getAttribute("templateEngine");
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        webContext = ThymeleafUtil.buildWebContext(req, resp, getServletContext());

        try {
            super.service(req, resp);
        } catch (InvalidParameterException e) {
            webContext.setVariable("error", e.getMessage());
            templateEngine.process("error", webContext, resp.getWriter());
        } catch (Exception e) {
            templateEngine.process("error", webContext, resp.getWriter());
        }
    }

    public static Optional<Cookie> findCookieByName(Cookie[] cookies, String name) {
        if (cookies == null) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst();
    }

    public static boolean isSessionExpired(Session session) {
        LocalDateTime expiresAt = session.getExpiresAt();
        LocalDateTime now = LocalDateTime.now();

        return now.isAfter(expiresAt);
    }
}
