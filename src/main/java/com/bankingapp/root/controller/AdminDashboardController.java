package com.bankingapp.root.controller;

import com.bankingapp.root.dto.AccountDTO;
import com.bankingapp.root.dto.FundsTransferDTO;
import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.service.AccountService;
import com.bankingapp.root.service.FundsTransferService;
import com.bankingapp.root.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    private final UserService userService;
    private final AccountService accountService;
    private final FundsTransferService fundsTransferService;

    public AdminDashboardController(UserService userService,
                                    AccountService accountService,
                                    FundsTransferService fundsTransferService) {
        this.userService = userService;
        this.accountService = accountService;
        this.fundsTransferService = fundsTransferService;
    }

    @GetMapping("/dashboard")
    public String AdminDashboard(Model model, Principal principal) {
        String username = principal.getName();
        if (username == null) return "redirect:/auth/loginForm";
        final UserDTO user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        List<UserDTO> users = userService.getAllUsers();
        model.addAttribute("users", users);
        List<AccountDTO> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        List<FundsTransferDTO> transfers = fundsTransferService.getAllTransfers();
        model.addAttribute("transfers", transfers);
        return "admin-dashboard";
    }
}
