package ru.bakery.rep.servlet;

import ru.bakery.rep.model.Product;
import ru.bakery.rep.service.ProductService;
import ru.bakery.rep.util.ImageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/admin/products")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 6 * 1024 * 1024)
public class AdminProductsServlet extends HttpServlet {

    private ProductService service() {
        return (ProductService) getServletContext().getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("products", service().getAll());
            request.getRequestDispatcher("/WEB-INF/views/admin/products.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("delete".equals(action)) {
                service().delete(Long.parseLong(request.getParameter("id")));
            } else {
                Product product = new Product();
                String id = request.getParameter("id");
                if (id != null && !id.isBlank()) {
                    product.setProductId(Long.parseLong(id));
                }

                String name = request.getParameter("name");
                product.setName(name == null ? "" : name.trim());
                product.setCategory(request.getParameter("category"));
                product.setDescription(request.getParameter("description"));
                product.setSalePrice(new BigDecimal(request.getParameter("price")));
                product.setAvailable(request.getParameter("available") != null);

                // Получаем файл из HTML-формы. На диск его не сохраняем.
                Part imagePart = request.getPart("image");
                if (imagePart != null && imagePart.getSize() > 0) {
                    byte[] image = imagePart.getInputStream().readAllBytes();
                    if (ImageUtil.getContentType(image) == null) {
                        throw new IllegalArgumentException("Выберите корректное изображение JPG, PNG или WEBP");
                    }
                    product.setImage(image);
                }

                if (product.getProductId() == 0) {
                    service().save(product);
                } else {
                    service().update(product);
                }
            }

            response.sendRedirect(request.getContextPath() + "/admin/products");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        } catch (Exception e) {
            throw new ServletException("Ошибка изменения товара", e);
        }
    }
}
