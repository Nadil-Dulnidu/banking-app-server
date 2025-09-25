package com.bankingapp.root.mapper;

import com.bankingapp.root.dto.FundsTransferDTO;
import com.bankingapp.root.entity.FundsTransferEntity;

public class FundsTransferDTOEntityMapper {
    public static FundsTransferDTO map(final FundsTransferEntity fundsTransferEntity) {
        if (fundsTransferEntity == null)
            throw new IllegalArgumentException("FundsTransferEntity cannot be null");
        final FundsTransferDTO fundsTransferDTO = new FundsTransferDTO();
        fundsTransferDTO.setId(fundsTransferEntity.getId());
        fundsTransferDTO.setFromAccount(fundsTransferEntity.getFromAccount());
        fundsTransferDTO.setToAccount(fundsTransferEntity.getToAccount());
        fundsTransferDTO.setBeneficiaryName(fundsTransferEntity.getBeneficiaryName());
        fundsTransferDTO.setAmount(fundsTransferEntity.getAmount());
        fundsTransferDTO.setDescription(fundsTransferEntity.getDescription());
        fundsTransferDTO.setStatus(fundsTransferEntity.getStatus());
        fundsTransferDTO.setTransferType(fundsTransferEntity.getTransferType());
        fundsTransferDTO.setCreatedAt(fundsTransferEntity.getCreatedAt());
        fundsTransferDTO.setScheduledAt(fundsTransferEntity.getScheduledAt());
        return fundsTransferDTO;
    }


    public static FundsTransferEntity map(final FundsTransferDTO fundsTransferDTO) {
        if (fundsTransferDTO == null)
            throw new IllegalArgumentException("FundsTransferDTO cannot be null");
        final FundsTransferEntity fundsTransferEntity = new FundsTransferEntity();
        fundsTransferEntity.setFromAccount(fundsTransferDTO.getFromAccount());
        fundsTransferEntity.setToAccount(fundsTransferDTO.getToAccount());
        fundsTransferEntity.setAmount(fundsTransferDTO.getAmount());
        fundsTransferEntity.setDescription(fundsTransferDTO.getDescription());
        fundsTransferEntity.setStatus(fundsTransferDTO.getStatus());
        fundsTransferEntity.setTransferType(fundsTransferDTO.getTransferType());
        fundsTransferEntity.setCreatedAt(fundsTransferDTO.getCreatedAt());
        fundsTransferEntity.setBeneficiaryName(fundsTransferDTO.getBeneficiaryName());
        fundsTransferEntity.setScheduledAt(fundsTransferDTO.getScheduledAt());
        return fundsTransferEntity;
    }
}
