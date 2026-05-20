package com.tpdev.joysList.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/hello")
public class TestController {
    @GetMapping
    public String hello() {
        return "Hello, User.\n "
                + "Time is now " + new Date() + ".";
    }
}
