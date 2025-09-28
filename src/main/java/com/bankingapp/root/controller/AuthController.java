package com.bankingapp.root.controller;

import com.bankingapp.root.dto.AuthRequestDTO;
import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) {
        final UserDTO registeredUser = authService.registerUser(userDTO);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(
            @Valid @RequestBody final AuthRequestDTO authRequest,
            HttpServletResponse response) {
        final String token = authService.loginUser(authRequest, response);
        final Map<String, String> body = new HashMap<>();
        body.put("accessToken", token);
        return ResponseEntity.ok(body);
    }

    @GetMapping(value = "/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(HttpServletRequest request) {
        final Map<String,String> token = authService.refreshToken(request);
        return ResponseEntity.ok(token);
    }
}
