package ru.bakery.rep.repository;

import ru.bakery.rep.model.User;
import java.sql.SQLException;

public interface UserRepository {
    User findByEmail(String email) throws SQLException;
    void save(User user) throws SQLException;
}
