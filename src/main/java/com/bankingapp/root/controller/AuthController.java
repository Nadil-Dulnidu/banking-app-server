package com.bankingapp.root.controller;

import com.bankingapp.root.dto.AuthRequestDTO;
import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
@Validated
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDTO") @Valid UserDTO userDTO, RedirectAttributes redirectAttributes) {
        try {
            authService.registerUser(userDTO);
            return "redirect:/auth/loginForm";
        }catch (RuntimeException ex){
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
            return "redirect:/auth/registerForm";
        }
    }

    @PostMapping("/login")
    public String loginUser(Model model, RedirectAttributes redirectAttributes,
            @ModelAttribute("authRequestDTO") AuthRequestDTO authRequest,
            HttpServletRequest request) {
       try{
           model.addAttribute("authRequestDTO", authRequest);
           authService.login(authRequest, request);
           return "redirect:/customer/dashboard";
       }catch (RuntimeException ex){
           redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
           return "redirect:/auth/loginForm";
       }
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
