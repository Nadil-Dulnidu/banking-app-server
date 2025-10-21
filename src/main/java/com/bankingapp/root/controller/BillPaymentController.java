package com.bankingapp.root.controller;

import com.bankingapp.root.dto.AccountDTO;
import com.bankingapp.root.dto.BillPaymentDTO;
import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.service.AccountService;
import com.bankingapp.root.service.BillPaymentService;
import com.bankingapp.root.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/bill-payment")
public class BillPaymentController {
    private final BillPaymentService billPaymentService;
    private final AccountService accountService;
    private  final UserService userService;

    public BillPaymentController(BillPaymentService billPaymentService,
                                 AccountService accountService,
                                 UserService userService) {
        this.userService = userService;
        this.billPaymentService = billPaymentService;
        this.accountService = accountService;
    }

    @GetMapping("/billForm")
    public String showBillPaymentForm(Model model, Principal principal) {
        String username = principal.getName();
        if (username == null) return "redirect:/auth/loginForm";
        final UserDTO user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        List<AccountDTO> accounts = accountService.getAllAccountsByUser(principal.getName());
        model.addAttribute("accounts", accounts);
        model.addAttribute("billPayment", new BillPaymentDTO());
        return "pay-bill";
    }

    @PostMapping("/create")
    public String createBillPayment(@ModelAttribute("billPayment") BillPaymentDTO billPaymentDTO) {
        billPaymentService.createPayment(billPaymentDTO);
        return "redirect:/customer/dashboard?successBillPayment";
    }

    @PostMapping("/delete/{id}")
    public String deleteBillPayment(@PathVariable Integer id) {
        billPaymentService.deletePayment(id);
        return "redirect:/customer/dashboard?deletedBillPayment";
    }
}
