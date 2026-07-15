package com.tpdev.joysList.entity;

import com.tpdev.joysList.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representation of a User for Listing Service context.
 * User details are populated from JWT claims. This entity does not represent a database table
 * in listing-service; rather, it is a DTO used in the Spring Security context principal.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;
}
