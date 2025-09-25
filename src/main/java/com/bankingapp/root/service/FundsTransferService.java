package com.bankingapp.root.service;

import com.bankingapp.root.common.Constants;
import com.bankingapp.root.dto.FundsTransferDTO;
import com.bankingapp.root.entity.FundsTransferEntity;
import com.bankingapp.root.exception.FundsTransferException;
import com.bankingapp.root.exception.TransferNotFoundException;
import com.bankingapp.root.mapper.FundsTransferDTOEntityMapper;
import com.bankingapp.root.repository.FundsTransferRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FundsTransferService {
    private final FundsTransferRepository fundsTransferRepository;

    public FundsTransferService (FundsTransferRepository fundsTransferRepository) {
        this.fundsTransferRepository = fundsTransferRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public FundsTransferDTO createTransfer (final FundsTransferDTO fundsTransferDTO) {
        if(fundsTransferRepository == null)
            throw new FundsTransferException("FundsTransferRepository cannot be null");
        final FundsTransferEntity fundsTransferEntity = FundsTransferDTOEntityMapper.map(fundsTransferDTO);
        if(fundsTransferDTO.getScheduledAt() != null){
            fundsTransferEntity.setStatus(Constants.TransferStatus.PENDING);
        }else{
            //check if both user back account exists
            //check sender back account has balance
            fundsTransferEntity.setStatus(Constants.TransferStatus.COMPLETED);
        }
        final LocalDateTime currentDateTime = LocalDateTime.now();
        fundsTransferEntity.setCreatedAt(currentDateTime);
        fundsTransferRepository.save(fundsTransferEntity);
        return FundsTransferDTOEntityMapper.map(fundsTransferEntity);
    }

    public List<FundsTransferDTO> getAllTransfers (
            final String fromAccount,
            final String toAccount,
            final Constants.TransferStatus transferStatus
    ) {
        final List<FundsTransferDTO> fundsTransferDTOS = fundsTransferRepository.findAll()
                .stream()
                .map(FundsTransferDTOEntityMapper::map)
                .filter(transfer -> fromAccount == null || fromAccount.isEmpty() ||
                        fromAccount.contains(transfer.getFromAccount()))
                .filter(transfer -> toAccount == null || toAccount.isEmpty() ||
                        toAccount.contains(transfer.getToAccount()))
                .filter(transfer -> transferStatus == null || transferStatus.equals(transfer.getStatus()))
                .toList();
        return fundsTransferDTOS;

    }

    public List<FundsTransferDTO> getAllTransfersByFromAccount (
            final String fromAccount,
            final Constants.TransferStatus transferStatus
    ) {
        final List<FundsTransferDTO> fundsTransferDTOS = fundsTransferRepository.findByFromAccount(fromAccount)
                .stream()
                .map(FundsTransferDTOEntityMapper::map)
                .filter(transfer -> transferStatus == null || transferStatus.equals(transfer.getStatus()))
                .toList();
        return fundsTransferDTOS;
    }

    public FundsTransferDTO getTransferById (final Integer transferId) {
        if(transferId == null)
            throw new FundsTransferException("Transfer id cannot be null");
        final FundsTransferEntity fundsTransferEntity = fundsTransferRepository.findById(transferId)
                .orElseThrow(() -> new TransferNotFoundException("transfer not found with id: " + transferId));
        return FundsTransferDTOEntityMapper.map(fundsTransferEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public FundsTransferDTO updateTransfer (final FundsTransferDTO fundsTransferDTO) {
        if(fundsTransferDTO == null)
            throw new FundsTransferException("fundsTransferDTO cannot be null");
        if(fundsTransferDTO.getScheduledAt() == null)
            throw new FundsTransferException("Scheduled at cannot be null");
        if(!fundsTransferDTO.getStatus().equals(Constants.TransferStatus.PENDING))
            throw new FundsTransferException("transfer status must be PENDING");
        final FundsTransferEntity fundsTransferEntity = fundsTransferRepository.findById(fundsTransferDTO.getId())
                .orElseThrow(() -> new TransferNotFoundException("transfer not found with id: " + fundsTransferDTO.getId()));
        fundsTransferEntity.setAmount(fundsTransferDTO.getAmount());
        fundsTransferEntity.setScheduledAt(fundsTransferDTO.getScheduledAt());
        fundsTransferRepository.save(fundsTransferEntity);
        return FundsTransferDTOEntityMapper.map(fundsTransferEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public FundsTransferDTO deleteTransfer (final Integer transferId) {
        if(transferId == null)
            throw new FundsTransferException("transferId cannot be null");
        final FundsTransferEntity fundsTransferEntity = fundsTransferRepository.findById(transferId)
                .orElseThrow(() -> new TransferNotFoundException("transfer not found with id: " + transferId));
        if(!fundsTransferEntity.getStatus().equals(Constants.TransferStatus.PENDING))
            throw new FundsTransferException("Cannot Delete: transfer status must be PENDING");
        fundsTransferRepository.delete(fundsTransferEntity);
        return FundsTransferDTOEntityMapper.map(fundsTransferEntity);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional(rollbackFor = Exception.class)
    public void processScheduledTransfers() {
        final LocalDateTime nowTime = LocalDateTime.now();
        final List<FundsTransferEntity> pendingTransfers = fundsTransferRepository.findAll()
                .stream()
                .filter(transfer ->
                        transfer.getStatus() == Constants.TransferStatus.PENDING
                        && transfer.getScheduledAt() != null
                        && transfer.getScheduledAt().isBefore(nowTime))
                .toList();
        for (FundsTransferEntity transfer : pendingTransfers) {
            // Check account balances
            // Deduct from sender
            // Add to receiver
            transfer.setStatus(Constants.TransferStatus.COMPLETED);
            fundsTransferRepository.save(transfer);
        }
    }
}
