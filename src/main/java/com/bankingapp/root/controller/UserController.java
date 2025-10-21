package com.bankingapp.root.controller;

import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/update")
    public String updateProfile(@ModelAttribute("user") UserDTO updatedUser) {
        userService.updateUser(updatedUser);
        return "redirect:/customer/dashboard?success";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/delete")
    public String deleteProfile(Principal principal, HttpSession session) {
        String username = principal.getName();
        userService.deleteUserByUsername(username);
        session.invalidate();
        return "redirect:/auth/login?accountDeleted";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/userid/{id}")
    public String deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return "redirect:/admin/dashboard?userDeleted";
    }
}