package com.bankingapp.root.controller;

import com.bankingapp.root.common.Constants;
import com.bankingapp.root.dto.FundsTransferDTO;
import com.bankingapp.root.service.FundsTransferService;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transfer")
@Validated
public class FundsTransferController {
    private final FundsTransferService fundsTransferService;
    public FundsTransferController(FundsTransferService fundsTransferService) {
        this.fundsTransferService = fundsTransferService;
    }

    @PostMapping()
    @ResponseBody
    public FundsTransferDTO createTransfer (
            @RequestBody final FundsTransferDTO fundsTransferDTO) {
        final FundsTransferDTO savedTransfer = fundsTransferService.createTransfer(fundsTransferDTO);
        return savedTransfer;
    }

    @GetMapping()
    @ResponseBody
    public List<FundsTransferDTO> getAllFundsTransfers(
            @RequestParam(required = false, value = "fromAccount") final String fromAccount,
            @RequestParam(required = false, value = "toAccount") final String toAccount,
            @RequestParam(required = false, value = "status") final Constants.TransferStatus status) {
        final List<FundsTransferDTO> transferDTOList = fundsTransferService.getAllTransfers(fromAccount, toAccount, status);
        return transferDTOList;
    }

    @PutMapping()
    @ResponseBody
    public FundsTransferDTO updateTransfer (
            @RequestBody final FundsTransferDTO fundsTransferDTO) {
        final FundsTransferDTO savedTransfer = fundsTransferService.updateTransfer(fundsTransferDTO);
        return savedTransfer;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public FundsTransferDTO getFundsTransferById(
            @Min (value = 1, message = "Transfer id must be a positive integer")
            @PathVariable final Integer id) {
        final FundsTransferDTO fundsTransferDTO = fundsTransferService.getTransferById(id);
        return fundsTransferDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public FundsTransferDTO deleteFundsTransferById(
            @Min(value = 1, message = "Transfer id must be a positive integer")
            @PathVariable final Integer id) {
        final FundsTransferDTO transferDTO = fundsTransferService.deleteTransfer(id);
        return transferDTO;
    }

    @GetMapping("/account/{account}")
    @ResponseBody
    public List<FundsTransferDTO> getFundsTransfersByAccount(
            @PathVariable final String account,
            @RequestParam(required = false, value = "status") final Constants.TransferStatus status) {
        final List<FundsTransferDTO> fundsTransferDTOS = fundsTransferService.getAllTransfersByFromAccount(account, status);
        return fundsTransferDTOS;
    }
}
