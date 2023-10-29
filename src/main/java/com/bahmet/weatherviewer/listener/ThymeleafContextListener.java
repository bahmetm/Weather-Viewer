package com.bahmet.weatherviewer.listener;

import com.bahmet.weatherviewer.util.ThymeleafUtil;
import org.thymeleaf.TemplateEngine;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ThymeleafContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        TemplateEngine templateEngine = ThymeleafUtil.buildTemplateEngine(servletContext);

        servletContext.setAttribute("templateEngine", templateEngine);
    }
}
