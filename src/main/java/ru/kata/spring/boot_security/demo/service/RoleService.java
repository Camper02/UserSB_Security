/* интерфейс для работы с ролями */


package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll();
    Optional<Role> findByName(String name);
    Role save(Role role);

    Long count();

    void saveAll(List<Role> adminRole);
}
