package com.bankingapp.root.service;

import com.bankingapp.root.common.Constants;
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

    public void createPayment(BillPaymentDTO payment) {
        if (payment == null) {
            throw new BillPaymentException("payment cannot be null");
        }
        BillPaymentEntity billPaymentEntity = BillPaymentDTOEntityMapper.map(payment);
        AccountEntity accountEntity = accountRepository.findByAccountNumber(payment.getAccountNumber())
                .orElseThrow(() -> new AccountException("account not found"));
        if(accountEntity.getBalance().compareTo(billPaymentEntity.getAmount()) < 0){
            throw new AccountException("Not enough balance to pay this bill.");
        }
        billPaymentEntity.setAccount(accountEntity);
        billPaymentEntity.setPaymentStatus(Constants.PaymentStatus.PENDING);
        billPaymentEntity.setPaymentDate(LocalDateTime.now());
        BillPaymentEntity savedBill = billPaymentRepository.save(billPaymentEntity);
        BillPaymentDTOEntityMapper.map(savedBill);
    }

    public void ConfirmPayment(Integer id) {
        if (id == null)
            throw new BillPaymentException("id cannot be null");
        BillPaymentEntity billPaymentEntity = billPaymentRepository.findById(id)
                .orElseThrow(() -> new BillPaymentException("bill payment not found"));
        AccountEntity accountEntity = accountRepository.findByAccountNumber(billPaymentEntity.getAccount().getAccountNumber())
                .orElseThrow(() -> new AccountException("account not found"));
        accountEntity.setBalance(accountEntity.getBalance() - billPaymentEntity.getAmount());
        billPaymentEntity.setPaymentStatus(Constants.PaymentStatus.COMPLETED);
        accountRepository.save(accountEntity);
        billPaymentRepository.save(billPaymentEntity);
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

    public void deletePayment(Integer id) {
        if (id == null)
            throw new BillPaymentException("id cannot be null");
        BillPaymentEntity billPaymentEntity = billPaymentRepository.findById(id)
                .orElseThrow(() -> new BillPaymentException("bill payment not found"));
        billPaymentRepository.delete(billPaymentEntity);
        BillPaymentDTOEntityMapper.map(billPaymentEntity);

    }
}
