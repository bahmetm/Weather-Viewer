package com.bahmet.weatherviewer.listener;

import com.bahmet.weatherviewer.dao.LocationDAO;
import com.bahmet.weatherviewer.dao.SessionDAO;
import com.bahmet.weatherviewer.dao.UserDAO;
import com.bahmet.weatherviewer.service.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.net.http.HttpClient;

@WebListener
public class ApplicationServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        try {
            Class.forName("com.bahmet.weatherviewer.util.PersistenceUtil");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        UserDAO userDAO = new UserDAO();
        SessionDAO sessionDAO = new SessionDAO();
        LocationDAO locationDAO = new LocationDAO();
        AuthService authService = new AuthService(userDAO, sessionDAO);
        SessionService sessionService = new SessionService(sessionDAO);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        HttpClient httpClient = HttpClient.newHttpClient();
        GeoCodingApiService geoCodingApiService = new GeoCodingApiService(httpClient, objectMapper);
        WeatherApiService weatherApiService = new WeatherApiService(httpClient, objectMapper);

        WeatherService weatherService = new WeatherService(weatherApiService, geoCodingApiService, locationDAO, userDAO);

        context.setAttribute("userDAO", userDAO);
        context.setAttribute("sessionDAO", sessionDAO);
        context.setAttribute("locationDAO", locationDAO);
        context.setAttribute("authService", authService);
        context.setAttribute("geoCodingApiService", geoCodingApiService);
        context.setAttribute("weatherService", weatherService);

        sessionService.deleteExpiredSessions();
    }
}
