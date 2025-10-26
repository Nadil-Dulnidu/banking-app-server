package com.bankingapp.root.service;

import com.bankingapp.root.common.Constants;
import com.bankingapp.root.dto.LoanApplicationDTO;
import com.bankingapp.root.entity.AccountEntity;
import com.bankingapp.root.entity.LoanApplicationEntity;
import com.bankingapp.root.mapper.LoanDTOEntityMapper;
import com.bankingapp.root.repository.AccountRepository;
import com.bankingapp.root.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final AccountRepository accountRepository;

    public LoanService(LoanRepository loanRepository, AccountRepository accountRepository) {
        this.loanRepository = loanRepository;
        this.accountRepository = accountRepository;
    }

    public LoanApplicationDTO createLoanApplication(LoanApplicationDTO loanApplicationDTO, String username) {
        if (loanApplicationDTO == null || username == null) {
            throw new IllegalArgumentException("Loan type must not be null");
        }
        AccountEntity accountEntity = accountRepository.findByUser_Username(username).stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Account not found for user"));
        LoanApplicationEntity loanApplicationEntity = LoanDTOEntityMapper.map(loanApplicationDTO);
        loanApplicationEntity.setAccount(accountEntity);
        loanApplicationEntity.setStatus(Constants.LoanStatus.PENDING);
        LoanApplicationEntity savedEntity = loanRepository.save(loanApplicationEntity);
        return LoanDTOEntityMapper.map(savedEntity);
    }

    public LoanApplicationDTO getLoanApplicationById(Integer id) {
        LoanApplicationEntity loanApplicationEntity = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan application not found"));
        return LoanDTOEntityMapper.map(loanApplicationEntity);
    }

    public List<LoanApplicationDTO> getLoansByUsername(String username) {
        List<LoanApplicationEntity> loanEntities = loanRepository.findByAccount_User_Username(username);
        return loanEntities.stream()
                .map(LoanDTOEntityMapper::map)
                .toList();
    }

    public List<LoanApplicationDTO> getLoanApplicationsByUsername(String username) {
        return getLoansByUsername(username);
    }

    public List<LoanApplicationDTO> getAllLoanApplications() {
        List<LoanApplicationEntity> loanEntities = loanRepository.findAll();
        return loanEntities.stream()
                .map(LoanDTOEntityMapper::map)
                .toList();
    }

    public LoanApplicationDTO updateLoanStatus(Integer id, Constants.LoanStatus status) {
        LoanApplicationEntity loanApplicationEntity = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan application not found"));
        loanApplicationEntity.setStatus(status);
        LoanApplicationEntity updatedEntity = loanRepository.save(loanApplicationEntity);
        return LoanDTOEntityMapper.map(updatedEntity);
    }

    public LoanApplicationDTO updateLoanApplication(LoanApplicationDTO loanApplicationDTO) {
        if (loanApplicationDTO == null || loanApplicationDTO.getId() == null) {
            throw new IllegalArgumentException("Loan application or ID must not be null");
        }
        LoanApplicationEntity existingEntity = loanRepository.findById(loanApplicationDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Loan application not found"));
        existingEntity.setAmount(loanApplicationDTO.getAmount());
        existingEntity.setTermMonths(loanApplicationDTO.getTermMonths());
        existingEntity.setPurpose(loanApplicationDTO.getPurpose());
        existingEntity.setAnnualIncome(loanApplicationDTO.getAnnualIncome());
        LoanApplicationEntity updatedEntity = loanRepository.save(existingEntity);
        return LoanDTOEntityMapper.map(updatedEntity);
    }

    public LoanApplicationDTO deleteLoanApplication(Integer id) {
        LoanApplicationEntity loanApplicationEntity = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan application not found"));
        loanRepository.delete(loanApplicationEntity);
        return LoanDTOEntityMapper.map(loanApplicationEntity);
    }
}
