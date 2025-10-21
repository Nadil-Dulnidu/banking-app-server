package com.bankingapp.root.controller;

import com.bankingapp.root.dto.AccountDTO;
import com.bankingapp.root.dto.FundsTransferDTO;
import com.bankingapp.root.dto.UserDTO;
import com.bankingapp.root.service.AccountService;
import com.bankingapp.root.service.FundsTransferService;
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
@RequestMapping("/transfer")
@Validated
public class FundsTransferController {
    private final FundsTransferService fundsTransferService;
    private final AccountService accountService;
    private final UserService userService;
    public FundsTransferController(FundsTransferService fundsTransferService,
                                   AccountService accountService,
                                   UserService userService) {
        this.fundsTransferService = fundsTransferService;
        this.accountService = accountService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/transactionForm")
    public String transferPage(Model model, Principal principal) {
        String username = principal.getName();
        if (username == null) return "redirect:/auth/loginForm";
        final UserDTO user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("transferForm", new FundsTransferDTO());
        List<AccountDTO> accounts = accountService.getAllAccountsByUser(principal.getName());
        model.addAttribute("accounts", accounts);
        return "transaction";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create")
    public String createFundsTransfer(@ModelAttribute("transferForm") @Valid FundsTransferDTO fundsTransferDTO,
                                      RedirectAttributes redirectAttributes) {
        try{
            fundsTransferService.createTransfer(fundsTransferDTO);
            return "redirect:/customer/dashboard?success=true";
        }catch (RuntimeException ex){
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
            return "redirect:/transfer/transactionForm";
        }
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/update")
    public String updateTransfer(@ModelAttribute FundsTransferDTO updatedTransfer) {
        fundsTransferService.updateTransfer(updatedTransfer);
        return "redirect:/customer/dashboard";
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/delete/{id}")
    public String deleteTransfer(
            @PathVariable final Integer id) {
        fundsTransferService.deleteTransfer(id);
        return "redirect:/customer/dashboard";
    }
}
