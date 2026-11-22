package ru.bakery.rep.repository;

import ru.bakery.config.DatabaseConnection;
import ru.bakery.rep.model.User;

import java.sql.*;

public class JdbcUserRepository implements UserRepository {

    @Override
    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT u.user_id, u.email, u.password_hash, u.role, u.customer_id, c.first_name, c.last_name " + "FROM users u LEFT JOIN customers c ON c.customer_id = u.customer_id WHERE u.email = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) return null;
                User user = new User();
                user.setUserId(result.getLong("user_id"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password_hash"));
                user.setRole(result.getString("role"));
                long customerId = result.getLong("customer_id");
                if (!result.wasNull()) user.setCustomerId(customerId);
                user.setFirstName(result.getString("first_name"));
                user.setLastName(result.getString("last_name"));
                return user;
            }
        }
    }

    @Override
    public void save(User user) throws SQLException {
        String customerSql = "INSERT INTO customers(first_name, last_name, email) VALUES (?, ?, ?) RETURNING customer_id";
        String userSql = "INSERT INTO users(email, password_hash, role, customer_id) VALUES (?, ?, 'CUSTOMER', ?)";
        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);
            try {
                long customerId;
                try (PreparedStatement statement = connection.prepareStatement(customerSql)) {
                    statement.setString(1, user.getFirstName());
                    statement.setString(2, user.getLastName());
                    statement.setString(3, user.getEmail());
                    try (ResultSet result = statement.executeQuery()) {
                        result.next();
                        customerId = result.getLong(1);
                    }
                }
                try (PreparedStatement statement = connection.prepareStatement(userSql)) {
                    statement.setString(1, user.getEmail());
                    statement.setString(2, user.getPassword());
                    statement.setLong(3, customerId);
                    statement.executeUpdate();
                }
                connection.commit();
            }
            catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }
}
