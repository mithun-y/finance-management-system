package com.assignment.financemanagementsystem.config;

import com.assignment.financemanagementsystem.model.Role;
import com.assignment.financemanagementsystem.model.Status;
import com.assignment.financemanagementsystem.model.User;
import com.assignment.financemanagementsystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            // Run only if DB is empty  -> This is to add the First ADMIN to the DB, so this admin can add,modify other data
            if (userRepository.count() == 0) {

                User admin = User.builder()
                        .name("Admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("password")) // already encoded
                        .role(Role.ADMIN)
                        .status(Status.ACTIVE)
                        .build();

                userRepository.save(admin);

                System.out.println("Initial data inserted");
            }
        };
    }
}