package com.bankingapp.root.controller;

import com.bankingapp.root.common.Constants;
import com.bankingapp.root.dto.AccountDTO;
import com.bankingapp.root.dto.NotificationDTO;
import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.service.AccountService;
import com.bankingapp.root.service.NotificationService;
import com.bankingapp.root.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/account")
@Validated
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;
    private final NotificationService notificationService;

    public AccountController(AccountService accountService, UserService userService, NotificationService notificationService) {
        this.accountService = accountService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create")
    public String createAccount(@ModelAttribute("accountDTO") @Valid AccountDTO account,
                                Principal principal,
                                RedirectAttributes redirectAttributes) {
        try{
            String username = principal.getName();
            if (username == null) return "redirect:/auth/loginForm";
            accountService.createAccount(account, username);
            NotificationDTO notificationDTO = new NotificationDTO(
                    username,
                    "Account Created Successfully",
                    Constants.NotificationType.ACCOUNT,
                    "Account created successfully with account number: " + account.getAccountNumber());
            notificationService.sendNotification(notificationDTO);
            return  "redirect:/customer/dashboard?accountCreatedSuccess";
        }catch (RuntimeException ex){
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
            return "redirect:/account/accountForm";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("update/{id}/status")
    public String updateStatus(
            @PathVariable Integer id,
            @RequestParam(value = "status") final Constants.AccountStatus status) {
        accountService.updateStatus(id, status);
        return "redirect:/admin/dashboard?accountStatusUpdated";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/close/{id}")
    public String closeAccount(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            accountService.closeAccount(id);
            return "redirect:/admin/dashboard?accountClosed";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
            return "redirect:/admin/dashboard?accountCloseError";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            accountService.deleteAccount(id);
            return "redirect:/admin/dashboard?accountDeleted";
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
            return "redirect:/admin/dashboard?accountDeleteError";
        }
    }

    @PreAuthorize("hasRole('CUSTOMER')")
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
