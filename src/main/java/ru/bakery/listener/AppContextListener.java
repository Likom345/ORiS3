package ru.bakery.listener;

import ru.bakery.rep.repository.*;
import ru.bakery.rep.service.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override public void contextInitialized(ServletContextEvent event) {
        UserRepository users = new JdbcUserRepository();
        ProductRepository products = new JdbcProductRepository();
        OrderRepository orders = new JdbcOrderRepository();
        event.getServletContext().setAttribute("authService", new AuthService(users));
        event.getServletContext().setAttribute("productRepository", products);
        event.getServletContext().setAttribute("productService", new ProductService(products));
        event.getServletContext().setAttribute("orderRepository", orders);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
