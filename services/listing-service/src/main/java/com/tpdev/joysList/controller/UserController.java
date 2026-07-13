package com.tpdev.joysList.controller;

import com.tpdev.joysList.constants.ApiConstants;
import com.tpdev.joysList.entity.UserEntity;
import com.tpdev.joysList.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.USERS)
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping(ApiConstants.ID)
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @GetMapping(ApiConstants.EMAIL)
    public ResponseEntity<UserEntity> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(service.getUserByEmail(email));
    }
}
