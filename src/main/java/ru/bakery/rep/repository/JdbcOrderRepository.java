package ru.bakery.rep.repository;

import ru.bakery.config.DatabaseConnection;
import ru.bakery.rep.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcOrderRepository implements OrderRepository {

    private Order map(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getLong("order_id"));
        order.setCustomerId(rs.getLong("customer_id"));
        order.setOrderDate(rs.getTimestamp("order_date"));
        order.setStatus(rs.getString("status"));
        order.setComment(rs.getString("comment"));
        return order;
    }

    @Override
    public long create(Order order) throws SQLException {
        String sql = "INSERT INTO orders(customer_id, status, comment) VALUES (?, 'Новый', ?) RETURNING order_id";
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, order.getCustomerId());
            ps.setString(2, order.getComment());
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getLong(1);
            }
        }
    }

    @Override
    public long createWithItems(long customerId, String comment, java.util.Map<Long, Integer> items) throws SQLException {
        String orderSql = "INSERT INTO orders(customer_id, status, comment) VALUES (?, 'Новый', ?) RETURNING order_id";
        String employeeSql = "SELECT employee_id FROM employees ORDER BY employee_id LIMIT 1";
        String itemSql = "INSERT INTO order_items(order_id, product_id, employee_id, quantity, unit_price) SELECT ?, p.product_id, ?, ?, p.sale_price FROM products p WHERE p.product_id=? AND p.is_available=TRUE";
        try (Connection c = DatabaseConnection.getConnection()) {
            c.setAutoCommit(false);
            try {
                long employeeId;
                try (PreparedStatement ps = c.prepareStatement(employeeSql); ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) throw new SQLException("Сначала добавьте хотя бы одного сотрудника в таблицу employees");
                    employeeId = rs.getLong(1);
                }
                long orderId;
                try (PreparedStatement ps = c.prepareStatement(orderSql)) {
                    ps.setLong(1, customerId);
                    ps.setString(2, comment);
                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();
                        orderId = rs.getLong(1);
                    }
                }
                try (PreparedStatement ps = c.prepareStatement(itemSql)) {
                    for (java.util.Map.Entry<Long, Integer> entry : items.entrySet()) {
                        ps.setLong(1, orderId);
                        ps.setLong(2, employeeId);
                        ps.setInt(3, entry.getValue());
                        ps.setLong(4, entry.getKey());
                        if (ps.executeUpdate() == 0) throw new SQLException("Один из товаров больше недоступен");
                    }
                }
                c.commit();
                return orderId;
            }
            catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }

    @Override
    public List<Order> findByCustomer(long customerId) throws SQLException {
        return find("SELECT * FROM orders WHERE customer_id=? ORDER BY order_date DESC", customerId);
    }

    @Override
    public List<Order> findAll() throws SQLException {
        return find("SELECT * FROM orders ORDER BY order_date DESC", null);
    }

    private List<Order> find(String sql, Long customerId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            if (customerId != null) ps.setLong(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) orders.add(map(rs));
            }
        }
        return orders;
    }

    @Override
    public void updateStatus(long id, String status) throws SQLException {
        try (Connection c = DatabaseConnection.getConnection(); PreparedStatement ps = c.prepareStatement("UPDATE orders SET status=? WHERE order_id=?")) {
            ps.setString(1, status);
            ps.setLong(2, id);
            ps.executeUpdate();
        }
    }
}
