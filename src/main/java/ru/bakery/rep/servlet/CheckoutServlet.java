package ru.bakery.rep.servlet;

import ru.bakery.rep.model.User;
import ru.bakery.rep.repository.OrderRepository;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;
@WebServlet("/checkout") public class CheckoutServlet extends HttpServlet {
    @SuppressWarnings("unchecked") @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        User user=(User)req.getSession().getAttribute("user");
        if(user==null) {
            resp.sendRedirect(req.getContextPath()+"/login");
            return;
        }
        Map<Long,Integer> cart=(Map<Long,Integer>)req.getSession().getAttribute("cart");
        if(cart==null||cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath()+"/cart");
            return;
        }
        if(user.getCustomerId()==null) {
            resp.sendError(400);
            return;
        }
        try {
            long id=((OrderRepository)getServletContext().getAttribute("orderRepository")).createWithItems(user.getCustomerId(),req.getParameter("comment"),cart);
            cart.clear();
            resp.sendRedirect(req.getContextPath()+"/orders?created="+id);
        }
        catch(Exception e) {
            req.getSession().setAttribute("checkoutError",e.getMessage());
            resp.sendRedirect(req.getContextPath()+"/cart");
        }
    }
}
