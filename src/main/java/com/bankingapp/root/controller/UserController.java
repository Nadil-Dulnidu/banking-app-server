package com.bankingapp.root.controller;

import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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

    @PutMapping
    @ResponseBody
    public UserDTO updateUser(@Valid @RequestBody UserDTO user) {
        return userService.updateUser(user);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public UserDTO getUser(
            @Valid
            @Min(value = 1, message = "User ID must be a positive integer")
            @PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping
    @ResponseBody
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/username/{username}")
    @ResponseBody
    public UserDTO getUserByUsername(@Valid @PathVariable final String username) {
        return userService.getUserByUsername(username);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public UserDTO deleteUser(
            @Valid
            @Min(value = 1, message = "User ID must be a positive integer")
            @PathVariable Integer id) {
        return userService.deleteUserById(id);
    }

    @DeleteMapping("/username/{username}")
    @ResponseBody
    public UserDTO deleteUser(@PathVariable String username) {
        return userService.deleteUserByUsername(username);
    }
}