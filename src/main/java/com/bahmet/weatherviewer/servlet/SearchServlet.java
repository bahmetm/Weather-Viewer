package com.bahmet.weatherviewer.servlet;

import com.bahmet.weatherviewer.dao.LocationDAO;
import com.bahmet.weatherviewer.dao.SessionDAO;
import com.bahmet.weatherviewer.dao.UserDAO;
import com.bahmet.weatherviewer.exception.SessionExpiredException;
import com.bahmet.weatherviewer.exception.SessionNotFoundException;
import com.bahmet.weatherviewer.exception.UnauthorizedUserException;
import com.bahmet.weatherviewer.model.Session;
import com.bahmet.weatherviewer.model.User;
import com.bahmet.weatherviewer.service.GeoCodingApiService;
import com.bahmet.weatherviewer.service.WeatherService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.UUID;

@WebServlet("/search")
public class SearchServlet extends BaseServlet {
    SessionDAO sessionDAO;
    LocationDAO locationDAO;
    GeoCodingApiService geocodingApiService;
    UserDAO userDAO;

    WeatherService weatherService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sessionDAO = (SessionDAO) config.getServletContext().getAttribute("sessionDAO");
        locationDAO = (LocationDAO) config.getServletContext().getAttribute("locationDAO");
        geocodingApiService = (GeoCodingApiService) config.getServletContext().getAttribute("geoCodingApiService");
        userDAO = (UserDAO) config.getServletContext().getAttribute("userDAO");
        weatherService = (WeatherService) config.getServletContext().getAttribute("weatherService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Cookie cookie = findCookieByName(cookies, "session_id").orElseThrow(() -> new UnauthorizedUserException("Attempt to search location without authorization."));

        UUID sessionId = UUID.fromString(cookie.getValue());

        Session session = sessionDAO.findById(sessionId).orElseThrow(() -> new SessionNotFoundException("Session not found."));

        if (isSessionExpired(session)) {
            throw new SessionExpiredException("Session expired.");
        }

        User user = session.getUser();

        String searchQuery = req.getParameter("query");

        if (searchQuery == null || searchQuery.isEmpty()) {
            throw new InvalidParameterException("Search query is not provided.");
        }

        weatherService.findLocations(searchQuery).ifPresent(locations -> webContext.setVariable("locations", locations));

        webContext.setVariable("search_query", searchQuery);
        webContext.setVariable("username", user.getUsername());

        templateEngine.process("search", webContext, resp.getWriter());
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

        String locationName = req.getParameter("location_name");
        String locationLatitudeParam = req.getParameter("location_latitude");
        String locationLongitudeParam = req.getParameter("location_longitude");

        if (locationName == null || locationName.isEmpty()) {
            throw new InvalidParameterException("Location name is not provided.");
        }

        if (locationLatitudeParam == null || locationLatitudeParam.isEmpty()) {
            throw new InvalidParameterException("Location latitude is not provided.");
        }

        if (locationLongitudeParam == null || locationLongitudeParam.isEmpty()) {
            throw new InvalidParameterException("Location longitude is not provided.");
        }

        weatherService.addUserLocation(user, locationName, locationLatitudeParam, locationLongitudeParam);

        resp.sendRedirect("/home");
    }
}
