package com.bankingapp.root.service;

import com.bankingapp.root.common.Constants;
import com.bankingapp.root.dto.FundsTransferDTO;
import com.bankingapp.root.entity.AccountEntity;
import com.bankingapp.root.entity.FundsTransferEntity;
import com.bankingapp.root.exception.AccountException;
import com.bankingapp.root.exception.FundsTransferException;
import com.bankingapp.root.exception.TransferNotFoundException;
import com.bankingapp.root.mapper.FundsTransferDTOEntityMapper;
import com.bankingapp.root.repository.AccountRepository;
import com.bankingapp.root.repository.FundsTransferRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FundsTransferService {
    private final FundsTransferRepository fundsTransferRepository;
    private final AccountRepository accountRepository;

    public FundsTransferService(FundsTransferRepository fundsTransferRepository, AccountRepository accountRepository) {
        this.fundsTransferRepository = fundsTransferRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createTransfer(final FundsTransferDTO fundsTransferDTO) {
        if (fundsTransferRepository == null)
            throw new FundsTransferException("FundsTransferRepository cannot be null");
        final FundsTransferEntity fundsTransferEntity = FundsTransferDTOEntityMapper.map(fundsTransferDTO);
        final AccountEntity senderAcc = accountRepository.findByAccountNumber(fundsTransferDTO.getFromAccount())
                .orElseThrow(() -> new AccountException("Sender Account not found"));
        if (fundsTransferDTO.getScheduledAt() != null) {
            fundsTransferEntity.setStatus(Constants.TransferStatus.PENDING);
        } else {

            if(senderAcc.getBalance().compareTo(fundsTransferDTO.getAmount()) == 0)
                throw new FundsTransferException("Transfer amount is insufficient");
            senderAcc.setBalance(senderAcc.getBalance() - fundsTransferDTO.getAmount());
            accountRepository.save(senderAcc);
            if(fundsTransferDTO.getTransferType().equals(Constants.TransferType.INTRA_BANK)){
                final AccountEntity receiverAcc = accountRepository.findByAccountNumber(fundsTransferDTO.getToAccount())
                        .orElseThrow(()->  new AccountException("Receiver Account not found"));
                receiverAcc.setBalance(receiverAcc.getBalance() + fundsTransferDTO.getAmount());
                accountRepository.save(receiverAcc);
            }
            fundsTransferEntity.setStatus(Constants.TransferStatus.COMPLETED);
        }
        final LocalDateTime currentDateTime = LocalDateTime.now();
        fundsTransferEntity.setCreatedAt(currentDateTime);
        fundsTransferEntity.setAccount(senderAcc);
        fundsTransferRepository.save(fundsTransferEntity);
        FundsTransferDTOEntityMapper.map(fundsTransferEntity);
    }

    public List<FundsTransferDTO> getAllTransfers() {
        final List<FundsTransferDTO> fundsTransferDTOS = fundsTransferRepository.findAll()
                .stream()
                .map(FundsTransferDTOEntityMapper::map)
                .toList();
        return fundsTransferDTOS;
    }


    @Transactional(rollbackFor = Exception.class)
    public void updateTransfer(final FundsTransferDTO fundsTransferDTO) {
        if (fundsTransferDTO == null)
            throw new FundsTransferException("fundsTransferDTO cannot be null");
        if (fundsTransferDTO.getScheduledAt() == null)
            throw new FundsTransferException("Scheduled at cannot be null");
        if (!fundsTransferDTO.getStatus().equals(Constants.TransferStatus.PENDING))
            throw new FundsTransferException("transfer status must be PENDING");
        final FundsTransferEntity fundsTransferEntity = fundsTransferRepository.findById(fundsTransferDTO.getId())
                .orElseThrow(() -> new TransferNotFoundException("transfer not found with id: " + fundsTransferDTO.getId()));
        fundsTransferEntity.setAmount(fundsTransferDTO.getAmount());
        fundsTransferEntity.setScheduledAt(fundsTransferDTO.getScheduledAt());
        fundsTransferRepository.save(fundsTransferEntity);
        FundsTransferDTOEntityMapper.map(fundsTransferEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteTransfer(final Integer transferId) {
        if (transferId == null)
            throw new FundsTransferException("transferId cannot be null");
        final FundsTransferEntity fundsTransferEntity = fundsTransferRepository.findById(transferId)
                .orElseThrow(() -> new TransferNotFoundException("transfer not found with id: " + transferId));
        if (!fundsTransferEntity.getStatus().equals(Constants.TransferStatus.PENDING))
            throw new FundsTransferException("Cannot Delete: transfer status must be PENDING");
        fundsTransferRepository.delete(fundsTransferEntity);
        FundsTransferDTOEntityMapper.map(fundsTransferEntity);
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
            final AccountEntity senderAcc = accountRepository.findByAccountNumber(transfer.getAccount().getAccountNumber())
                    .orElseThrow(() -> new AccountException("Sender Account not found"));
            if(senderAcc.getBalance().compareTo(transfer.getAmount()) == 0)
                throw new FundsTransferException("Transfer amount is insufficient");
            senderAcc.setBalance(senderAcc.getBalance() - transfer.getAmount());
            transfer.setStatus(Constants.TransferStatus.COMPLETED);
            accountRepository.save(senderAcc);
            if(transfer.getTransferType().equals(Constants.TransferType.INTRA_BANK)){
                final AccountEntity receiverAcc = accountRepository.findByAccountNumber(transfer.getAccount().getAccountNumber())
                        .orElseThrow(() -> new AccountException("Receiver Account not found"));
                receiverAcc.setBalance(receiverAcc.getBalance() + transfer.getAmount());
                accountRepository.save(receiverAcc);
            }
            fundsTransferRepository.save(transfer);
        }
    }

    public List<FundsTransferDTO> getFundsTransfersByUsername(final String username) {
        final List<FundsTransferDTO> fundsTransferDTOS =
                fundsTransferRepository.findAllByAccount_User_Username(username)
                .stream()
                .map(FundsTransferDTOEntityMapper::map)
                .toList();
        return fundsTransferDTOS;
    }
}
