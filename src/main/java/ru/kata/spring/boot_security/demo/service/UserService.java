/* интерфейс с методами CRUD для пользователей */


package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    void createUser(User user);
    void updateUser(User user);
    User getById(Long id);
    List<User> getAll();
    void delete(Long id);

    Optional<User> findByEmail(String mail);
}