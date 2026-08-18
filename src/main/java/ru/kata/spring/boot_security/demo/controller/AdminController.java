/* Обрабатывает запросы /admin/** (список, создание, редактирование, удаление). Использует UserService и RoleService. */


package ru.kata.spring.boot_security.demo.controller;



import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    //private final PasswordEncoder passwordEncoder; // для шифровки

    public AdminController(UserService userService,
                           RoleService roleService,
                           PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        //this.passwordEncoder = passwordEncoder; // для шифровки
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "admin/users";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/user-form";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        return "admin/user-form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        if (user.getId() == null) {
            // Новый пользователь – пароль обязателен
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                return "redirect:/admin/new?error=password_required";
            }
            // Шифрование
            // user.setPassword(passwordEncoder.encode(user.getPassword())); // шифр
        } else {
            // Редактирование
            User existing = userService.getById(user.getId());
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                user.setPassword(existing.getPassword()); // оставляем старый
            } else {
                // Новый пароль – сохраняем как есть
                // user.setPassword(passwordEncoder.encode(user.getPassword())); // шифр
                user.setPassword(user.getPassword());
            }
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
