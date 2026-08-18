/* контроллер для логина / регистрации */


package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // Показываем страницу логина (Spring Security обрабатывает POST /login)
    @GetMapping("/login")
    public String loginPage() {
        return "login";   // шаблон login.html
    }

    @GetMapping("/")
    public String homePage() {
        return "index";   // шаблон index.html
    }

    // Если нужна страница регистрации – можно добавить
    // @GetMapping("/registration")
    // public String registrationPage() { return "registration"; }
}