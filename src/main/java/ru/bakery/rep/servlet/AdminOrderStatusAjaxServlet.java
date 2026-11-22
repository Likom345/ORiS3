package ru.bakery.rep.servlet;

import ru.bakery.rep.repository.OrderRepository;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
@WebServlet("/admin/ajax/order-status") public class AdminOrderStatusAjaxServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String status=req.getParameter("status");
        if(!Arrays.asList("Новый","В работе","Готов","Выдан","Отменён").contains(status)) {
            resp.setStatus(400);
            resp.getWriter().write("{\"message\":\"Некорректный статус\"}");
            return;
        }
        try {
            long id=Long.parseLong(req.getParameter("id"));
            ((OrderRepository)getServletContext().getAttribute("orderRepository")).updateStatus(id,status);
            resp.getWriter().write("{\"message\":\"Статус обновлён\",\"status\":\""+status+"\"}");
        }
        catch(Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"message\":\"Не удалось обновить статус\"}");
        }
    }
}
