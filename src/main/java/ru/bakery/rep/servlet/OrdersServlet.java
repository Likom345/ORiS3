package ru.bakery.rep.servlet;

import ru.bakery.rep.model.User;
import ru.bakery.rep.repository.OrderRepository;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
@WebServlet("/orders") public class OrdersServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        User user=(User)req.getSession().getAttribute("user");
        if(user==null) {
            resp.sendRedirect(req.getContextPath()+"/login");
            return;
        }
        try {
            req.setAttribute("orders",((OrderRepository)getServletContext().getAttribute("orderRepository")).findByCustomer(user.getCustomerId()));
            req.getRequestDispatcher("/WEB-INF/views/orders/list.jsp").forward(req,resp);
        }
        catch(Exception e) {
            throw new ServletException(e);
        }
    }
}
