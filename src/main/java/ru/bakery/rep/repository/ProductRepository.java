package ru.bakery.rep.repository;

import ru.bakery.rep.model.Product;
import java.sql.SQLException;
import java.util.List;

public interface ProductRepository {
    List<Product> findAll() throws SQLException;
    Product findById(long id) throws SQLException;
    byte[] findImageById(long id) throws SQLException;
    void save(Product product) throws SQLException;
    void update(Product product) throws SQLException;
    void delete(long id) throws SQLException;
}
