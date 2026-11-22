package ru.bakery.rep.servlet;

import ru.bakery.rep.service.ProductService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
@WebServlet("") public class HomeServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ProductService service = (ProductService) getServletContext().getAttribute("productService");
            req.setAttribute("products", service.getAll());
            req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);
        }
        catch (Exception e) {
            throw new ServletException("Не удалось загрузить каталог", e);
        }
    }
}
