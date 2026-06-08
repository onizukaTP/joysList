package com.tpdev.joysList.service;

import com.tpdev.events.UserRegisteredEvent;
import com.tpdev.joysList.entity.UserEntity;
import com.tpdev.joysList.entity.enums.Role;
import com.tpdev.joysList.kafka.producer.UserEventProducer;
import com.tpdev.joysList.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    private final UserEventProducer userEventProducer;

    public UserEntity createUser(UserEntity user) {
        // encode password
        user.setPassword(encoder.encode(user.getPassword()));

        userRepository.save(user);

        userEventProducer.publishUserRegistered(
                new UserRegisteredEvent(
                        user.getId(),
                        user.getEmail(),
                        user.getUsername()
                )
        );
        log.info("Published User Registered Event");
        return user;
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }
}
