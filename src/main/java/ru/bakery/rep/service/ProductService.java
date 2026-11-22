package ru.bakery.rep.service;

import ru.bakery.rep.model.Product;
import ru.bakery.rep.repository.ProductRepository;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() throws SQLException {
        return repository.findAll();
    }

    public Product get(long id) throws SQLException {
        return repository.findById(id);
    }

    public void save(Product p) throws SQLException {
        validate(p);
        repository.save(p);
    }

    public void update(Product p) throws SQLException {
        validate(p);
        repository.update(p);
    }

    public void delete(long id) throws SQLException {
        if (id <= 0) throw new IllegalArgumentException("Некорректный товар");
        repository.delete(id);
    }

    private void validate(Product p) {
        if (p.getName() == null || p.getName().isBlank()) throw new IllegalArgumentException("Введите название товара");
        if (p.getCategory() == null || !List.of("Хлеб", "Выпечка", "Торт", "Пирожное", "Другое").contains(p.getCategory())) throw new IllegalArgumentException("Выберите категорию");
        if (p.getSalePrice() == null || p.getSalePrice().compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Цена должна быть неотрицательной");
    }
}
