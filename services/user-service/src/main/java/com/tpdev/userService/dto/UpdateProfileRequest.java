package com.tpdev.userService.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    @Size(max = 500, message = "Bio must be at most 500 characters")
    private String bio;

    @Size(max = 20, message = "Phone must be at most 20 characters")
    private String phone;

    @Size(max = 100, message = "Location must be at most 100 characters")
    private String location;
}
