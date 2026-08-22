package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.awt.*;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	@Value("${server.port:8080}")
	private String port;

	@Value("${server.servlet.context-path:}")
	private String contextPath;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(RoleService roleRepository,
                                  UserService userRepository) {
		return args -> {
			// Создаём роли, если их ещё нет
			if (roleRepository.count() == 0) {
				Role adminRole = new Role("ROLE_ADMIN");
				Role userRole = new Role("ROLE_USER");
				roleRepository.saveAll(List.of(adminRole, userRole));
			}

			// Создаём администратора, если его нет
			if (userRepository.findByEmail("admin@example.com").isEmpty()) {
				User admin = new User();
				admin.setName("Admin");
				admin.setEmail("admin@example.com");
				admin.setPassword("admin");
				// Даём все роли
				Set<Role> allRoles = new HashSet<>(roleRepository.findAll());
				admin.setRoles(allRoles);
				userRepository.createUser(admin);
			}
		};
	}

	@EventListener(ApplicationReadyEvent.class)
	public void openBrowser() {
		try {
			String url = "http://localhost:" + port + contextPath;
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().browse(new URI(url));
			} else {
				// fallback для систем без Desktop
				String os = System.getProperty("os.name").toLowerCase();
				Runtime rt = Runtime.getRuntime();
				if (os.contains("win")) {
					rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
				} else if (os.contains("mac")) {
					rt.exec("open " + url);
				} else if (os.contains("nix") || os.contains("nux")) {
					rt.exec("xdg-open " + url);
				}
			}
		} catch (Exception e) {
			System.err.println("Не удалось открыть браузер: " + e.getMessage());
		}
	}

}
