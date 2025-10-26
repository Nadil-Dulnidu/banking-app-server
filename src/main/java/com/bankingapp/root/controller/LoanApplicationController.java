package com.bankingapp.root.controller;

import com.bankingapp.root.common.Constants;
import com.bankingapp.root.dto.LoanApplicationDTO;
import com.bankingapp.root.dto.NotificationDTO;
import com.bankingapp.root.service.LoanService;
import com.bankingapp.root.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/loan")
public class LoanApplicationController {

    public final LoanService loanService;
    public final NotificationService notificationService;

    public LoanApplicationController(LoanService loanService, NotificationService notificationService) {
        this.loanService = loanService;
        this.notificationService = notificationService;
    }

    @GetMapping("/loanApplication")
    public String applyForLoan(Model model) {
        LoanApplicationDTO loanApplicationDTO = new LoanApplicationDTO();
        model.addAttribute("loanApplication", loanApplicationDTO);
        return "loan-application";
    }

    @PostMapping("/submit")
    public String submitLoanApplication(@ModelAttribute("loanApplication") LoanApplicationDTO loanApplicationDTO, Principal principal, RedirectAttributes redirectAttributes) {
        try{
            String username = principal.getName();
            loanService.createLoanApplication(loanApplicationDTO, username);
            NotificationDTO notificationDTO = new NotificationDTO(
                    username,
                    "Loan Application Submitted",
                    Constants.NotificationType.TRANSACTION,
                    "Your loan application has been submitted successfully and is under review.");
            notificationService.sendNotification(notificationDTO);
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("errMessage", e.getMessage());
            return "redirect:/loan/loanApplication";
        }
        return "redirect:/customer/dashboard?loanApplicationSubmitted=success";
    }

    @GetMapping("update/{id}/status")
    public String updateStatus(
            @PathVariable Integer id,
            @RequestParam(value = "status") final Constants.LoanStatus status) {
        loanService.updateLoanStatus(id, status);
        return "redirect:/admin/dashboard?accountStatusUpdated";
    }

    @PostMapping("/edit/{id}")
    public String editLoanApplication(
            @ModelAttribute("loanApplication") LoanApplicationDTO loanApplicationDTO) {
        loanService.updateLoanApplication(loanApplicationDTO);
        return "redirect:/customer/dashboard?loanApplicationUpdated";
    }

    @GetMapping("/delete/{id}")
    public String deleteLoanApplication(@PathVariable Integer id) {
        loanService.deleteLoanApplication(id);
        return "redirect:/customer/dashboard?loanApplicationDeleted";
    }
}
