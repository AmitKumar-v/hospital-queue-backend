package com.hospital.queue_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hospital.queue_system.model.User;
import com.hospital.queue_system.repository.UserRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // Create Admin
        if (!userRepository.existsByEmail("admin@hospital.com")) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@hospital.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("✅ Admin created: admin@hospital.com / admin123");
        }

        // Create Receptionist
        if (!userRepository.existsByEmail("receptionist@hospital.com")) {
            User receptionist = new User();
            receptionist.setName("Receptionist");
            receptionist.setEmail("receptionist@hospital.com");
            receptionist.setPassword(passwordEncoder.encode("recep123"));
            receptionist.setRole("RECEPTIONIST");
            userRepository.save(receptionist);
            System.out.println("✅ Receptionist created: receptionist@hospital.com / recep123");
        }
    }
}
