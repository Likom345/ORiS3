package ru.bakery.rep.servlet;

import ru.bakery.rep.repository.ProductRepository;
import ru.bakery.rep.util.ImageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product-image")
public class ProductImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long productId = Long.parseLong(request.getParameter("id"));
            ProductRepository repository = (ProductRepository)
                    getServletContext().getAttribute("productRepository");

            byte[] image = repository.findImageById(productId);
            String contentType = ImageUtil.getContentType(image);

            if (contentType == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            response.setContentType(contentType);
            response.setContentLength(image.length);
            response.setHeader("X-Content-Type-Options", "nosniff");
            response.setHeader("Cache-Control", "public, max-age=3600");
            response.getOutputStream().write(image);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            throw new ServletException("Не удалось загрузить изображение товара", e);
        }
    }
}
