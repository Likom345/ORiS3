package ru.bakery.rep.servlet;

import ru.bakery.rep.model.User;
import ru.bakery.rep.service.AuthService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
@WebServlet("/login") public class LoginServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = ((AuthService)getServletContext().getAttribute("authService")).login(req.getParameter("email"), req.getParameter("password"));
            if (user == null) {
                req.setAttribute("error", "Неверный email или пароль");
                doGet(req, resp);
                return;
            }
            HttpSession old = req.getSession(false);
            if (old != null) old.invalidate();
            req.getSession(true).setAttribute("user", user);
            resp.sendRedirect(req.getContextPath()+"/");
        }
        catch (Exception e) {
            throw new ServletException("Ошибка входа", e);
        }
    }
}
