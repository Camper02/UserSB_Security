/* интерфейс с методами CRUD для пользователей */


package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;
import java.util.List;

public interface UserService {
    void save(User user);
    User getById(Long id);
    List<User> getAll();
    void delete(Long id);
}