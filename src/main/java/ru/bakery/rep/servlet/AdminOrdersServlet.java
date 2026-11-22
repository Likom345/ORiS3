package ru.bakery.rep.servlet;

import ru.bakery.rep.repository.OrderRepository;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
@WebServlet("/admin/orders") public class AdminOrdersServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        try {
            OrderRepository r=(OrderRepository)getServletContext().getAttribute("orderRepository");
            req.setAttribute("orders",r.findAll());
            req.getRequestDispatcher("/WEB-INF/views/admin/orders.jsp").forward(req,resp);
        }
        catch(Exception e) {
            throw new ServletException(e);
        }
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        String status=req.getParameter("status");
        if(!java.util.Arrays.asList("Новый","В работе","Готов","Выдан","Отменён").contains(status)) {
            resp.sendError(400);
            return;
        }
        try {
            ((OrderRepository)getServletContext().getAttribute("orderRepository")).updateStatus(Long.parseLong(req.getParameter("id")),status);
            resp.sendRedirect(req.getContextPath()+"/admin/orders");
        }
        catch(Exception e) {
            throw new ServletException(e);
        }
    }
}
