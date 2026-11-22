package ru.bakery.rep.service;

import org.mindrot.jbcrypt.BCrypt;
import ru.bakery.rep.model.User;
import ru.bakery.rep.repository.UserRepository;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AuthService {
    private final UserRepository userRepository;
    private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(String firstName, String lastName, String email, String password) throws SQLException {
        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank()) throw new IllegalArgumentException("Укажите имя и фамилию");
        if (email == null || !EMAIL.matcher(email).matches()) throw new IllegalArgumentException("Некорректный email");
        if (password == null || password.length() < 8) throw new IllegalArgumentException("Пароль должен содержать минимум 8 символов");
        if (userRepository.findByEmail(email) != null) throw new IllegalArgumentException("Пользователь с таким email уже существует");
        User user = new User();
        user.setFirstName(firstName.trim());
        user.setLastName(lastName.trim());
        user.setEmail(email.trim().toLowerCase());
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        userRepository.save(user);
    }

    public User login(String email, String password) throws SQLException {
        User user = userRepository.findByEmail(email == null ? "" : email.trim().toLowerCase());
        if (user == null || !BCrypt.checkpw(password == null ? "" : password, user.getPassword())) return null;
        user.setPassword(null);
        return user;
    }
}
