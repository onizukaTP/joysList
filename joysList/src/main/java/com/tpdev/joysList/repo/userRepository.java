package com.tpdev.joysList.repo;

import com.tpdev.joysList.Dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepository extends JpaRepository<Integer, UserDto> {
    Optional<String> findByUsername(String username);
    Boolean existsByUsername(String username);
}
