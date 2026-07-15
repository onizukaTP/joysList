package com.tpdev.joysList.service;

import com.tpdev.joysList.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity createUser(UserEntity user);

    UserEntity getUserByEmail(String email);

    UserEntity getUserById(Long id);

    List<UserEntity> getAll();
}
