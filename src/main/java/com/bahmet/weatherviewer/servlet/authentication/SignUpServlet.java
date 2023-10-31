package com.bahmet.weatherviewer.servlet.authentication;

import com.bahmet.weatherviewer.dao.SessionDAO;
import com.bahmet.weatherviewer.dao.UserDAO;
import com.bahmet.weatherviewer.model.User;
import com.bahmet.weatherviewer.service.AuthService;
import com.bahmet.weatherviewer.servlet.BaseServlet;
import com.bahmet.weatherviewer.util.ValidatorUtil;
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

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        templateEngine.process("sign_up", webContext, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        ValidatorUtil.validateAuthParameters(username, password);

        authService.signUp(username, password);

        resp.sendRedirect("/sign-in");
    }
}
