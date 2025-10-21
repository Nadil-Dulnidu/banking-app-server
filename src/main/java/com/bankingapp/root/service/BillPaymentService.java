package com.bankingapp.root.service;

import com.bankingapp.root.dto.BillPaymentDTO;
import com.bankingapp.root.entity.AccountEntity;
import com.bankingapp.root.entity.BillPaymentEntity;
import com.bankingapp.root.exception.AccountException;
import com.bankingapp.root.exception.BillPaymentException;
import com.bankingapp.root.mapper.BillPaymentDTOEntityMapper;
import com.bankingapp.root.repository.AccountRepository;
import com.bankingapp.root.repository.BillPaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillPaymentService {
    private final BillPaymentRepository billPaymentRepository;
    private final AccountRepository accountRepository;

    public BillPaymentService(BillPaymentRepository billPaymentRepository,
                              AccountRepository accountRepository) {
        this.billPaymentRepository = billPaymentRepository;
        this.accountRepository = accountRepository;
    }

    public BillPaymentDTO createPayment(BillPaymentDTO payment) {
        if (payment == null) {
            throw new BillPaymentException("payment cannot be null");
        }
        BillPaymentEntity billPaymentEntity = BillPaymentDTOEntityMapper.map(payment);
        AccountEntity accountEntity = accountRepository.findByAccountNumber(payment.getAccountNumber())
                .orElseThrow(() -> new AccountException("account not found"));
        if(accountEntity.getBalance().compareTo(billPaymentEntity.getAmount()) < 0){
            throw new AccountException("Not enough balance to pay this bill.");
        }
        accountEntity.setBalance(accountEntity.getBalance() - billPaymentEntity.getAmount());
        billPaymentEntity.setAccount(accountEntity);
        billPaymentEntity.setPaymentDate(LocalDateTime.now());
        accountRepository.save(accountEntity);
        BillPaymentEntity savedBill = billPaymentRepository.save(billPaymentEntity);
        return BillPaymentDTOEntityMapper.map(savedBill);
    }

    public BillPaymentDTO getPaymentById(Integer id) {
        if (id == null)
            throw new BillPaymentException("id cannot be null");
        BillPaymentEntity billPaymentEntity = billPaymentRepository.findById(id)
                .orElseThrow(() -> new BillPaymentException("bill payment not found"));
        return BillPaymentDTOEntityMapper.map(billPaymentEntity);
    }

    public List<BillPaymentDTO> getPaymentsByUsername(String username) {
        if (username == null)
            throw new BillPaymentException("accountNumber cannot be null");
        List<BillPaymentDTO> billPaymentDTOS = billPaymentRepository.findAllByAccount_User_Username(username)
                .stream()
                .map(BillPaymentDTOEntityMapper::map)
                .toList();
        return billPaymentDTOS;
    }

    public List<BillPaymentDTO> getPaymentsByAccountId(Integer accountId) {
        if (accountId == null)
            throw new BillPaymentException("accountNumber cannot be null");
        List<BillPaymentDTO> billPaymentDTOS = billPaymentRepository.findAllByAccount_Id(accountId)
                .stream()
                .map(BillPaymentDTOEntityMapper::map)
                .toList();
        return billPaymentDTOS;
    }

    public BillPaymentDTO deletePayment(Integer id) {
        if (id == null)
            throw new BillPaymentException("id cannot be null");
        BillPaymentEntity billPaymentEntity = billPaymentRepository.findById(id)
                .orElseThrow(() -> new BillPaymentException("bill payment not found"));
        billPaymentRepository.delete(billPaymentEntity);
        return BillPaymentDTOEntityMapper.map(billPaymentEntity);

    }
}
