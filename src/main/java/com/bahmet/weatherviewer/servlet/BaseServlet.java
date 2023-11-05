package com.bahmet.weatherviewer.servlet;

import com.bahmet.weatherviewer.exception.*;
import com.bahmet.weatherviewer.model.Session;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

public class BaseServlet extends HttpServlet {
    protected ITemplateEngine templateEngine;
    protected WebContext webContext;

    public static Optional<Cookie> findCookieByName(Cookie[] cookies, String name) {
        if (cookies == null) {
            return Optional.empty();
        }

        return Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(name)).findFirst();
    }

    public static boolean isSessionExpired(Session session) {
        LocalDateTime expiresAt = session.getExpiresAt();
        LocalDateTime now = LocalDateTime.now();

        return now.isAfter(expiresAt);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        templateEngine = (ITemplateEngine) config.getServletContext().getAttribute("templateEngine");
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JavaxServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp);
        webContext = new WebContext(webExchange);

        try {
            super.service(req, resp);
        } catch (UnauthorizedUserException | SessionNotFoundException | SessionExpiredException e) {
            webContext.setVariable("error", e);
            resp.sendRedirect("/sign_in");
        } catch (PasswordNotMatchException | UserNotFoundException e) {
            templateEngine.process("sign_in", webContext, resp.getWriter());
        } catch (UserExistsException e) {
            webContext.setVariable("error", e);
            templateEngine.process("sign_up", webContext, resp.getWriter());
        } catch (CookieNotFoundException e) {
            webContext.clearVariables();
            resp.sendRedirect("/sign_in");
        } catch (Exception e) {
            webContext.setVariable("error", e);
            templateEngine.process("error", webContext, resp.getWriter());
        }
    }
}
