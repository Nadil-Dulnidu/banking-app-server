package com.bankingapp.root.controller;

import com.bankingapp.root.dto.*;
import com.bankingapp.root.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class DashboardController {

    private final UserService userService;
    private final AccountService accountService;
    private final FundsTransferService fundsTransferService;
    private final BillPaymentService billPaymentService;
    private final NotificationService notificationService;
    private final LoanService loanService;

    public DashboardController(UserService userService,
                               AccountService accountService,
                               FundsTransferService fundsTransferService,
                               BillPaymentService billPaymentService,
                               NotificationService notificationService,
                               LoanService loanService) {
        this.userService = userService;
        this.accountService = accountService;
        this.fundsTransferService = fundsTransferService;
        this.billPaymentService = billPaymentService;
        this.notificationService = notificationService;
        this.loanService = loanService;
    }

    @GetMapping("/dashboard")
    public String showProfile(Principal principal, Model model) {
        String username = principal.getName();
        if (username == null) return "redirect:/auth/loginForm";
        final UserDTO user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        final List<AccountDTO> accounts = accountService.getAllAccountsByUser(username);
        model.addAttribute("accountList", accounts);
        final List<FundsTransferDTO> transfers = fundsTransferService.getFundsTransfersByUsername(username);
        model.addAttribute("transferList", transfers);
        final  List<BillPaymentDTO> payments = billPaymentService.getPaymentsByUsername(username);
        model.addAttribute("paymentList", payments);
        final List<NotificationDTO> notification = notificationService.getNotificationsByUsername(username);
        model.addAttribute("notificationList", notification);
        final List<LoanApplicationDTO> loanApplications = loanService.getLoanApplicationsByUsername(username);
        model.addAttribute("loanApplicationList", loanApplications);
        return "dashboard";
    }
}
