package com.bankingapp.root.controller;

import com.bankingapp.root.common.Constants;
import com.bankingapp.root.dto.AccountDTO;
import com.bankingapp.root.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping()
    @ResponseBody
    public AccountDTO createAccount(@RequestBody AccountDTO account) {
        return accountService.createAccount(account);
    }

    @GetMapping("/{accountId}")
    @ResponseBody
    public AccountDTO getAccount(@PathVariable Integer accountId) {
        return accountService.getAccount(accountId);
    }

    @GetMapping("/user/{userId}")
    @ResponseBody
    public List<AccountDTO> getAccountsByUser(@PathVariable Integer userId) {
        return accountService.getAccountsByUser(userId);
    }

    @PutMapping("/{id}/status")
    @ResponseBody
    public AccountDTO updateStatus(
            @PathVariable Integer id,
            @RequestParam(value = "status") final Constants.AccountStatus status) {
        return accountService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public AccountDTO deleteAccount(@PathVariable Integer id) {
        return accountService.deleteAccount(id);
    }

}
