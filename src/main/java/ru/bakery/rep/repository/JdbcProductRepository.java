package ru.bakery.rep.repository;

import ru.bakery.config.DatabaseConnection;
import ru.bakery.rep.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductRepository implements ProductRepository {

    // Не загружаем сами байты фотографий при получении списка товаров.
    // Вместо этого получаем только признак, есть ли фотография.
    private static final String PRODUCT_FIELDS =
            "product_id, name, category, description, sale_price, is_available, " +
            "(image IS NOT NULL) AS has_image";

    private Product mapProduct(ResultSet result) throws SQLException {
        Product product = new Product();
        product.setProductId(result.getLong("product_id"));
        product.setName(result.getString("name"));
        product.setCategory(result.getString("category"));
        product.setDescription(result.getString("description"));
        product.setSalePrice(result.getBigDecimal("sale_price"));
        product.setAvailable(result.getBoolean("is_available"));
        product.setHasImage(result.getBoolean("has_image"));
        return product;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT " + PRODUCT_FIELDS + " FROM products ORDER BY product_id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                products.add(mapProduct(result));
            }
        }
        return products;
    }

    @Override
    public Product findById(long id) throws SQLException {
        String sql = "SELECT " + PRODUCT_FIELDS + " FROM products WHERE product_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? mapProduct(result) : null;
            }
        }
    }

    @Override
    public byte[] findImageById(long id) throws SQLException {
        String sql = "SELECT image FROM products WHERE product_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getBytes("image");
                }
            }
        }
        return null;
    }

    @Override
    public void save(Product product) throws SQLException {
        String sql = "INSERT INTO products(name, category, description, sale_price, is_available, image) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setFields(statement, product);
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Product product) throws SQLException {
        // Если новое изображение не передано (null), оставляем старое.
        String sql = "UPDATE products SET name=?, category=?, description=?, sale_price=?, " +
                "is_available=?, image=COALESCE(?, image) WHERE product_id=?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setFields(statement, product);
            statement.setLong(7, product.getProductId());
            statement.executeUpdate();
        }
    }

    private void setFields(PreparedStatement statement, Product product) throws SQLException {
        statement.setString(1, product.getName());
        statement.setString(2, product.getCategory());
        statement.setString(3, product.getDescription());
        statement.setBigDecimal(4, product.getSalePrice());
        statement.setBoolean(5, product.isAvailable());
        statement.setBytes(6, product.getImage());
    }

    @Override
    public void delete(long id) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM products WHERE product_id=?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
