package com.tpdev.joysList.util;

import com.tpdev.joysList.entity.UserEntity;
import com.tpdev.joysList.entity.enums.Role;
import com.tpdev.joysList.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${ADMIN_PASSWORD:admin}")
    private String adminPassword;

    @Override
    public void run(String... args) {

        seedAdminUser();

    }

    private void seedAdminUser() {

        if (userRepository.existsByEmail(adminEmail)) {
            log.info("Admin already exists.");
            return;
        }

        UserEntity admin = new UserEntity();

        admin.setUsername(adminUsername);
        admin.setEmail(adminEmail);

        admin.setPassword(
                passwordEncoder.encode(adminPassword)
        );

        admin.setRole(Role.ADMIN);

        userRepository.save(admin);

        log.info(
                "Default admin user created successfully."
        );
    }
}
