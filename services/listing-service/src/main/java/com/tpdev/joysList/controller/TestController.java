package com.tpdev.joysList.controller;

import com.tpdev.joysList.constants.ApiConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(ApiConstants.TEST)
@Slf4j
public class TestController {
    @GetMapping
    public String hello() {
        log.info("Testing...");
        return "Hello, User.\n "
                + "Time is now " + new Date() + ".";
    }
}
