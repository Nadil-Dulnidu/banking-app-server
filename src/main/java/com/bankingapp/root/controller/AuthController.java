package com.bankingapp.root.controller;

import com.bankingapp.root.dto.AuthRequestDTO;
import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDTO") UserDTO userDTO) {
        authService.registerUser(userDTO);
        return "redirect:/auth/loginForm";
    }

    @PostMapping("/login")
    public String loginUser(Model model,
            @ModelAttribute("authRequestDTO") AuthRequestDTO authRequest,
            HttpServletRequest request) {
        model.addAttribute("authRequestDTO", authRequest);
        authService.login(authRequest, request);
        return "redirect:/customer/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/auth/loginForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(Model model) {
        AuthRequestDTO authRequestDTO = new AuthRequestDTO();
        model.addAttribute("authRequestDTO", authRequestDTO);
        return "login";
    }

    @GetMapping("/registerForm")
    public String registerForm(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        return "register";
    }
}
