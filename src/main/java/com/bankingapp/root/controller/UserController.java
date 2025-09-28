package com.bankingapp.root.controller;

import com.bankingapp.root.common.Constants;
import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PutMapping()
    public ResponseEntity<UserDTO> updateUser(
            @Valid @RequestBody UserDTO user) {
        UserDTO updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize(Constants.ADMIN)
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(
            @Valid
            @Min(value = 1, message = "User ID must be a positive integer")
            @PathVariable Integer id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize(Constants.ADMIN)
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(
            @Valid @PathVariable final String username) {
        UserDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize(Constants.ADMIN)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDTO> deleteUser(
            @Valid
            @Min(value = 1, message = "User ID must be a positive integer")
            @PathVariable Integer id) {
        UserDTO deletedUser = userService.deleteUserById(id);
        return ResponseEntity.ok(deletedUser);
    }

    @DeleteMapping(value = "username/{username}")
    public ResponseEntity<UserDTO> deleteUser(
            @PathVariable String username) {
        UserDTO deletedUser = userService.deleteUserByUsername(username);
        return ResponseEntity.ok(deletedUser);
    }
}