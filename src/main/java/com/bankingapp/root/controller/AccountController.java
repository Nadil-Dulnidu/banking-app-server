package com.bankingapp.root.controller;

import com.bankingapp.root.common.Constants;
import com.bankingapp.root.dto.AccountDTO;
import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.service.AccountService;
import com.bankingapp.root.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public String createAccount(@ModelAttribute("accountDTO") AccountDTO account, Principal principal) {
        String username = principal.getName();
        if (username == null) return "redirect:/auth/loginForm";
        accountService.createAccount(account, username);
        return  "redirect:/customer/dashboard?accountCreatedSuccess";
    }

    @GetMapping("update/{id}/status")
    public String updateStatus(
            @PathVariable Integer id,
            @RequestParam(value = "status") final Constants.AccountStatus status) {
        accountService.updateStatus(id, status);
        return "redirect:/admin/dashboard?accountStatusUpdated";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Integer id) {
        accountService.deleteAccount(id);
        return "redirect:/admin/dashboard?accountDeleted";
    }

    @GetMapping("/accountForm")
    public String showAccountForm(Model model, Principal principal) {
        String username = principal.getName();
        if (username == null) return "redirect:/auth/loginForm";
        final UserDTO user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("accountDTO", new AccountDTO());
        return "add-account";
    }
}
