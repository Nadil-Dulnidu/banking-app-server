package com.bankingapp.root.mapper;

import com.bankingapp.root.dto.BillPaymentDTO;
import com.bankingapp.root.entity.BillPaymentEntity;

public class BillPaymentDTOEntityMapper {

    public static BillPaymentDTO map (BillPaymentEntity billPaymentEntity) {
        if (billPaymentEntity == null) {
            throw new IllegalArgumentException("billPaymentEntity cannot be null");
        }
        BillPaymentDTO billPaymentDTO = new BillPaymentDTO();
        billPaymentDTO.setId(billPaymentEntity.getId());
        billPaymentDTO.setAmount(billPaymentEntity.getAmount());
        billPaymentDTO.setAccountNumber(billPaymentEntity.getAccount().getAccountNumber());
        billPaymentDTO.setPaymentFrequency(billPaymentEntity.getPaymentFrequency());
        billPaymentDTO.setBillerName(billPaymentEntity.getBillerName());
        billPaymentDTO.setPaymentDate(billPaymentEntity.getPaymentDate());
        billPaymentDTO.setPaymentStatus(billPaymentEntity.getPaymentStatus());
        return billPaymentDTO;
    }

    public static BillPaymentEntity map (BillPaymentDTO billPaymentDTO) {
        if (billPaymentDTO == null) {
            throw new IllegalArgumentException("billPaymentDTO cannot be null");
        }
        BillPaymentEntity billPaymentEntity = new BillPaymentEntity();
        billPaymentEntity.setId(billPaymentDTO.getId());
        billPaymentEntity.setAmount(billPaymentDTO.getAmount());
        billPaymentEntity.setBillerName(billPaymentDTO.getBillerName());
        billPaymentEntity.setPaymentFrequency(billPaymentDTO.getPaymentFrequency());
        billPaymentEntity.setPaymentDate(billPaymentDTO.getPaymentDate());
        billPaymentEntity.setPaymentStatus(billPaymentDTO.getPaymentStatus());
        return billPaymentEntity;
    }
}
