package ru.bakery.rep.servlet;

import ru.bakery.rep.service.AuthService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
@WebServlet("/register") public class RegisterServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(req, resp);
    }
    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String first = req.getParameter("firstName"), last = req.getParameter("lastName"), email = req.getParameter("email"), password = req.getParameter("password");
        if (password == null || !password.equals(req.getParameter("confirmPassword"))) {
            req.setAttribute("error", "Пароли не совпадают");
            doGet(req, resp);
            return;
        }
        try {
            ((AuthService)getServletContext().getAttribute("authService")).register(first,last,email,password);
            resp.sendRedirect(req.getContextPath()+"/login?registered=1");
        }
        catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
        catch (Exception e) {
            throw new ServletException("Ошибка регистрации", e);
        }
    }
}
