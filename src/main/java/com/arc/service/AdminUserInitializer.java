package com.arc.service;

import com.arc.entity.Role;
import com.arc.entity.Users;
import com.arc.repository.UserDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer {

    @Bean
    public CommandLineRunner createAdminUser(UserDetailsRepository userRepository,
                                             PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if the admin user already exists
            if (userRepository.findByUsername("admin").isEmpty()) {
                // Create a new admin user
                Users admin = new Users();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin1234")); // You should encode the password in a real application
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
                System.out.println("Default Admin user created: username=admin, password=admin1234");
            }
            if (userRepository.findByUsername("user").isEmpty()) {
                // Create a new admin user
                Users admin = new Users();
                admin.setUsername("user");
                admin.setPassword(passwordEncoder.encode("user1234")); // You should encode the password in a real application
                admin.setRole(Role.USER);
                userRepository.save(admin);
                System.out.println("Default User user created: username=user, password=user1234");
            }
        };
    }
}
