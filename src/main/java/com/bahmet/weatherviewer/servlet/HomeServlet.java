package com.bahmet.weatherviewer.servlet;

import com.bahmet.weatherviewer.dao.LocationDAO;
import com.bahmet.weatherviewer.dao.SessionDAO;
import com.bahmet.weatherviewer.dao.UserDAO;
import com.bahmet.weatherviewer.dto.WeatherDTO;
import com.bahmet.weatherviewer.dto.WeatherApiResponse;
import com.bahmet.weatherviewer.dto.weatherEnum.TimeOfDay;
import com.bahmet.weatherviewer.dto.weatherEnum.WeatherCondition;
import com.bahmet.weatherviewer.exception.SessionExpiredException;
import com.bahmet.weatherviewer.exception.SessionNotFoundException;
import com.bahmet.weatherviewer.exception.UnauthorizedUserException;
import com.bahmet.weatherviewer.model.Location;
import com.bahmet.weatherviewer.model.Session;
import com.bahmet.weatherviewer.model.User;
import com.bahmet.weatherviewer.service.WeatherApiService;
import com.bahmet.weatherviewer.service.WeatherService;
import org.hibernate.Hibernate;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.*;

@WebServlet(urlPatterns = "/home")
public class HomeServlet extends BaseServlet {
    SessionDAO sessionDAO;
    WeatherService weatherService;
    LocationDAO locationDAO;

    UserDAO userDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sessionDAO = (SessionDAO) config.getServletContext().getAttribute("sessionDAO");
        weatherService = (WeatherService) config.getServletContext().getAttribute("weatherService");
        locationDAO = (LocationDAO) config.getServletContext().getAttribute("locationDAO");
        userDAO = (UserDAO) config.getServletContext().getAttribute("userDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();

        Optional<Cookie> cookieOptional = findCookieByName(cookies, "session_id");

        if (cookieOptional.isPresent()) {
            Cookie cookie = cookieOptional.get();

            UUID sessionId = UUID.fromString(cookie.getValue());

            Session session = sessionDAO.findById(sessionId).orElseThrow(() -> new SessionNotFoundException("Session not found."));

            if (isSessionExpired(session)) {
                throw new SessionExpiredException("Session expired.");
            }

            User user = session.getUser();

            webContext.setVariable("username", user.getUsername());
            weatherService.getUserWeather(user).ifPresent(locationWeatherMap -> webContext.setVariable("locationWeatherMap", locationWeatherMap));
        }

        templateEngine.process("home", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Cookie cookie = findCookieByName(cookies, "session_id").orElseThrow(() -> new UnauthorizedUserException("Attempt to add location without authorization."));

        UUID sessionId = UUID.fromString(cookie.getValue());

        Session session = sessionDAO.findById(sessionId).orElseThrow(() -> new SessionNotFoundException("Session not found."));

        if (isSessionExpired(session)) {
            throw new SessionExpiredException("Session expired.");
        }

        User user = session.getUser();

        String locationIdParameter = req.getParameter("location_id");

        if (locationIdParameter == null || locationIdParameter.isEmpty()) {
            throw new InvalidParameterException("Location id is not provided.");
        }

        weatherService.removeUserLocation(user, locationIdParameter);

        resp.sendRedirect("/home");
    }
}
