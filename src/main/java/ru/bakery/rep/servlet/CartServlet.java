package ru.bakery.rep.servlet;

import ru.bakery.rep.model.Product;
import ru.bakery.rep.service.ProductService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;
@WebServlet("/cart") public class CartServlet extends HttpServlet {
    @SuppressWarnings("unchecked") private Map<Long,Integer> cart(HttpSession s) {
        Map<Long,Integer> cart=(Map<Long,Integer>)s.getAttribute("cart");
        if(cart==null) {
            cart=new LinkedHashMap<>();
            s.setAttribute("cart",cart);
        }
        return cart;
    }
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        try {
            Map<Long,Integer> cart=cart(req.getSession());
            List<Product> products=new ArrayList<>();
            ProductService service=(ProductService)getServletContext().getAttribute("productService");
            for(Long id:cart.keySet()) {
                Product p=service.get(id);
                if(p!=null)products.add(p);
            }
            req.setAttribute("cartProducts",products);
            req.setAttribute("cart",cart);
            req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req,resp);
        }
        catch(Exception e) {
            throw new ServletException(e);
        }
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException {
        Map<Long,Integer> cart=cart(req.getSession());
        String action=req.getParameter("action");
        try {
            long id=Long.parseLong(req.getParameter("id"));
            if("remove".equals(action))cart.remove(id);
            else {
                int quantity=Integer.parseInt(req.getParameter("quantity"));
                if(quantity<1||quantity>50)throw new IllegalArgumentException();
                cart.put(id,quantity);
            }
            resp.sendRedirect(req.getContextPath()+"/cart");
        }
        catch(Exception e) {
            resp.sendRedirect(req.getContextPath()+"/cart?error=1");
        }
    }
}
