package com.tpdev.joysList.service.impl;

import com.tpdev.events.UserRegisteredEvent;
import com.tpdev.joysList.entity.UserEntity;
import com.tpdev.joysList.kafka.producer.UserEventProducer;
import com.tpdev.joysList.repo.UserRepository;
import com.tpdev.joysList.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserEventProducer userEventProducer;

    @Override
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

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }
}
