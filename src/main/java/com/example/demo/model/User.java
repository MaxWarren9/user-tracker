package com.example.demo.model;

import com.example.demo.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String username;
    private String passwordHash;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}