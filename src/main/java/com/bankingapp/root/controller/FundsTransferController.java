package com.bankingapp.root.controller;

import com.bankingapp.root.common.Constants;
import com.bankingapp.root.dto.FundsTransferDTO;
import com.bankingapp.root.service.FundsTransferService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfer")
@Validated
public class FundsTransferController {
    private final FundsTransferService fundsTransferService;

    public FundsTransferController(FundsTransferService fundsTransferService) {
        this.fundsTransferService = fundsTransferService;
    }

    @PostMapping()
    public ResponseEntity<FundsTransferDTO> createTransfer (
            @Valid
            @RequestBody final FundsTransferDTO fundsTransferDTO) {
        final FundsTransferDTO savedTransfer = fundsTransferService.createTransfer(fundsTransferDTO);
        return ResponseEntity.ok().body(savedTransfer);
    }

    @GetMapping()
    public ResponseEntity<List<FundsTransferDTO>> getAllFundsTransfers(
            @RequestParam(required = false, value = "fromAccount") final String fromAccount,
            @RequestParam(required = false, value = "toAccount") final String toAccount,
            @RequestParam(required = false, value = "status") final Constants.TransferStatus status) {
        final List<FundsTransferDTO> transferDTOList = fundsTransferService.getAllTransfers(fromAccount, toAccount, status);
        return ResponseEntity.ok().body(transferDTOList);
    }

    @PutMapping()
    public ResponseEntity<FundsTransferDTO> updateTransfer (
            @Valid
            @RequestBody final FundsTransferDTO fundsTransferDTO) {
        final FundsTransferDTO savedTransfer = fundsTransferService.updateTransfer(fundsTransferDTO);
        return ResponseEntity.ok().body(savedTransfer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FundsTransferDTO> getFundsTransferById(
            @Valid
            @Min (value = 1, message = "Transfer id must be a positive integer")
            @PathVariable final Integer id) {
        final FundsTransferDTO fundsTransferDTO = fundsTransferService.getTransferById(id);
        return ResponseEntity.ok().body(fundsTransferDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FundsTransferDTO> deleteFundsTransferById(
            @Valid
            @Min(value = 1, message = "Transfer id must be a positive integer")
            @PathVariable final Integer id) {
        final FundsTransferDTO transferDTO = fundsTransferService.deleteTransfer(id);
        return ResponseEntity.ok().body(transferDTO);
    }

    @GetMapping("/account/{account}")
    public ResponseEntity<List<FundsTransferDTO>> getFundsTransfersByAccount(
            @PathVariable final String account,
            @RequestParam(required = false, value = "status") final Constants.TransferStatus status) {
        final List<FundsTransferDTO> fundsTransferDTOS = fundsTransferService.getAllTransfersByFromAccount(account, status);
        return ResponseEntity.ok().body(fundsTransferDTOS);
    }
}
