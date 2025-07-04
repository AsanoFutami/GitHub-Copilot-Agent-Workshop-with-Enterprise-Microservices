package com.skishop.auth.controller;

import com.skishop.auth.dto.request.UserCreateRequest;
import com.skishop.auth.dto.response.UserResponse;
import com.skishop.auth.service.UserRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * User Registration API Controller
 */
@RestController
@RequestMapping("/api/auth/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Registration API", description = "User registration and deletion API")
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    /**
     * Register a new user
     */
    @PostMapping
    @Operation(summary = "Register new user", description = "Creates a new user account and publishes an event")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User registration successful"),
        @ApiResponse(responseCode = "400", description = "Invalid request content"),
        @ApiResponse(responseCode = "409", description = "Email address or username already registered")
    })
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserCreateRequest request) {
        log.info("User registration request received for username: {}", request.getUsername());
        
        try {
            UserResponse response = userRegistrationService.registerUser(request);
            log.info("User registration completed for user: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            log.warn("User registration failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            log.error("Unexpected error during user registration: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * User deletion (soft delete)
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user", description = "Soft deletes a user and publishes an event")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "User deletion successful"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "403", description = "No permission")
    })
    public ResponseEntity<Void> deleteUser(
            @PathVariable UUID userId,
            @RequestParam(required = false, defaultValue = "Admin deletion") String reason) {
        log.info("User deletion request received for user: {} with reason: {}", userId, reason);
        
        try {
            userRegistrationService.deleteUser(userId, reason);
            log.info("User deletion completed for user: {}", userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.warn("User deletion failed: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Unexpected error during user deletion: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * User hard deletion (admin only)
     */
    @DeleteMapping("/{userId}/hard")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Hard delete user", description = "Physically deletes a user and publishes an event")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "User hard deletion successful"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "403", description = "No permission")
    })
    public ResponseEntity<Void> hardDeleteUser(
            @PathVariable UUID userId,
            @RequestParam(required = false, defaultValue = "Admin hard deletion") String reason) {
        log.info("User hard deletion request received for user: {} with reason: {}", userId, reason);
        
        try {
            userRegistrationService.hardDeleteUser(userId, reason);
            log.info("User hard deletion completed for user: {}", userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.warn("User hard deletion failed: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Unexpected error during user hard deletion: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
