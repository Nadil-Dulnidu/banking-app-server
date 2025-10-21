package com.bankingapp.root.controller;

import com.bankingapp.root.dto.AccountDTO;
import com.bankingapp.root.dto.BillPaymentDTO;
import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.service.AccountService;
import com.bankingapp.root.service.BillPaymentService;
import com.bankingapp.root.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/bill-payment")
@Validated
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

    @PreAuthorize("hasRole('CUSTOMER')")
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

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create")
    public String createBillPayment(@ModelAttribute("billPayment") @Valid BillPaymentDTO billPaymentDTO, RedirectAttributes redirectAttributes) {
        try{
            billPaymentService.createPayment(billPaymentDTO);
            return "redirect:/customer/dashboard?billPaymentSuccess";
        }catch (RuntimeException ex){
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
            return "redirect:/bill-payment/billForm";
        }
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/delete/{id}")
    public String deleteBillPayment(@PathVariable Integer id) {
        billPaymentService.deletePayment(id);
        return "redirect:/customer/dashboard?deletedBillPayment";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/confirm/{id}")
    public String confirmBillPayment(@PathVariable Integer id) {
        billPaymentService.ConfirmPayment(id);
        return "redirect:/customer/dashboard?confirmedBillPayment";
    }
}
