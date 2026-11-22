package ru.bakery.rep.repository;

import ru.bakery.rep.model.Order;
import java.sql.SQLException;
import java.util.List;

public interface OrderRepository {
    long create(Order order) throws SQLException;
    long createWithItems(long customerId, String comment, java.util.Map<Long, Integer> items) throws SQLException;
    List<Order> findByCustomer(long customerId) throws SQLException;
    List<Order> findAll() throws SQLException;
    void updateStatus(long id, String status) throws SQLException;
}
