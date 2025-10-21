package com.bankingapp.root.controller;

import com.bankingapp.root.dto.AccountDTO;
import com.bankingapp.root.dto.BillPaymentDTO;
import com.bankingapp.root.dto.FundsTransferDTO;
import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.service.AccountService;
import com.bankingapp.root.service.BillPaymentService;
import com.bankingapp.root.service.FundsTransferService;
import com.bankingapp.root.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    public DashboardController(UserService userService,
                               AccountService accountService,
                               FundsTransferService fundsTransferService,
                               BillPaymentService billPaymentService) {
        this.userService = userService;
        this.accountService = accountService;
        this.fundsTransferService = fundsTransferService;
        this.billPaymentService = billPaymentService;
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
        return "dashboard";
    }
}
