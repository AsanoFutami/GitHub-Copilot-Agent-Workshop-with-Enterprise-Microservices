package com.skishop.frontend.dto;

import java.time.LocalDateTime;

/**
 * User DTO
 */
public record UserDto(
    String id,
    String email,
    String firstName,
    String lastName,
    String phoneNumber,
    boolean active,
    String role,
    LocalDateTime createdAt
) {}
