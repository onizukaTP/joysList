package com.tpdev.userService.config;

import com.tpdev.userService.entity.UserEntity;
import com.tpdev.userService.entity.UserProfile;
import com.tpdev.userService.entity.enums.Role;
import com.tpdev.userService.repo.UserProfileRepository;
import com.tpdev.userService.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email:admin@joyslist.com}")
    private String adminEmail;

    @Value("${app.admin.username:admin}")
    private String adminUsername;

    @Value("${app.admin.password:Admin@123}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.existsByEmail(adminEmail)) {
            log.info("Admin user already exists — skipping seed.");
            return;
        }

        UserEntity admin = UserEntity.builder()
                .username(adminUsername)
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);
        userProfileRepository.save(UserProfile.builder().user(admin).build());

        log.info("Admin user seeded: username={}", adminUsername);
    }
}
