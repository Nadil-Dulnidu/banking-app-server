package com.bankingapp.root.controller;

import com.bankingapp.root.dto.BillPaymentDTO;
import com.bankingapp.root.service.BillPaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bill-payment")
public class BillPaymentController {
    private final BillPaymentService billPaymentService;

    public BillPaymentController(BillPaymentService billPaymentService) {
        this.billPaymentService = billPaymentService;
    }

    @PostMapping
    @ResponseBody
    public BillPaymentDTO createPayment(@RequestBody BillPaymentDTO payment) {
        return billPaymentService.createPayment(payment);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public BillPaymentDTO getPayment(@PathVariable Integer id) {
        return billPaymentService.getPaymentById(id);
    }

    @GetMapping("/account/{accountId}")
    @ResponseBody
    public List<BillPaymentDTO> getPaymentsByAccountId(@PathVariable Integer accountId) {
        return billPaymentService.getPaymentsByAccountId(accountId);
    }

    @GetMapping("/account/number/{accountNumber}")
    @ResponseBody
    public List<BillPaymentDTO> getPaymentsByAccountNumber(@PathVariable String accountNumber) {
        return billPaymentService.getPaymentsByAccountNumber(accountNumber);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public BillPaymentDTO deletePayment(@PathVariable Integer id) {
        return billPaymentService.deletePayment(id);
    }
}
