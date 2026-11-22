package ru.bakery.filter;

import ru.bakery.rep.model.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
@WebFilter("/admin/*") public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
