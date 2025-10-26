package com.bankingapp.root.mapper;

import com.bankingapp.root.dto.AccountDTO;
import com.bankingapp.root.dto.LoanApplicationDTO;
import com.bankingapp.root.entity.LoanApplicationEntity;
import com.bankingapp.root.mapper.AccountDTOEntityMapper;

public class LoanDTOEntityMapper {

    public static LoanApplicationDTO map(LoanApplicationEntity loanApplicationEntity){
        if(loanApplicationEntity == null)
            throw new IllegalArgumentException("Loan Application Entity is null");
        LoanApplicationDTO loanApplicationDTO = new LoanApplicationDTO();
        loanApplicationDTO.setId(loanApplicationEntity.getId());
        loanApplicationDTO.setAmount(loanApplicationEntity.getAmount());
        loanApplicationDTO.setTermMonths(loanApplicationEntity.getTermMonths());
        loanApplicationDTO.setStatus(loanApplicationEntity.getStatus());
        loanApplicationDTO.setCreatedAt(loanApplicationEntity.getCreatedAt());
        loanApplicationDTO.setLoanType(loanApplicationEntity.getLoanType());
        loanApplicationDTO.setPurpose(loanApplicationEntity.getPurpose());
        loanApplicationDTO.setAnnualIncome(loanApplicationEntity.getAnnualIncome());
        loanApplicationDTO.setAccountId(loanApplicationEntity.getAccount().getId());
        return loanApplicationDTO;
    }

    public  static LoanApplicationEntity map(LoanApplicationDTO loanApplicationDTO){
        if(loanApplicationDTO == null)
            throw new IllegalArgumentException("Loan Application DTO is null");
        LoanApplicationEntity loanApplicationEntity = new LoanApplicationEntity();
        loanApplicationEntity.setId(loanApplicationDTO.getId());
        loanApplicationEntity.setAmount(loanApplicationDTO.getAmount());
        loanApplicationEntity.setTermMonths(loanApplicationDTO.getTermMonths());
        loanApplicationEntity.setStatus(loanApplicationDTO.getStatus());
        loanApplicationEntity.setCreatedAt(loanApplicationDTO.getCreatedAt());
        loanApplicationEntity.setLoanType(loanApplicationDTO.getLoanType());
        loanApplicationEntity.setPurpose(loanApplicationDTO.getPurpose());
        loanApplicationEntity.setAnnualIncome(loanApplicationDTO.getAnnualIncome());
        return loanApplicationEntity;
    }
}
