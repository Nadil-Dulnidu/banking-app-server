package com.bankingapp.root.service;

import com.bankingapp.root.common.Constants;
import com.bankingapp.root.dto.AnnouncementRequestDTO;
import com.bankingapp.root.dto.NotificationDTO;
import com.bankingapp.root.entity.NotificationEntity;
import com.bankingapp.root.entity.UserEntity;
import com.bankingapp.root.exception.NotificationException;
import com.bankingapp.root.exception.UserNotFoundException;
import com.bankingapp.root.mapper.NotificationDTOEntityMapper;
import com.bankingapp.root.repository.NotificationRepository;
import com.bankingapp.root.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public NotificationDTO sendNotification(final NotificationDTO notificationDTO) {
        if (Objects.isNull(notificationDTO)) {
            throw new NotificationException("User ID or message cannot be null");
        }
        if (notificationDTO.getMessage().isBlank()) {
            throw new NotificationException("message cannot be blank");
        }
        final UserEntity userEntity = userRepository.findByUsername(notificationDTO.getUsername())
                .orElseThrow(() -> {
                    return new UserNotFoundException("User not found");
                });
        final NotificationEntity notificationEntity = NotificationDTOEntityMapper.map(notificationDTO);
        notificationEntity.setUser(userEntity);
        final NotificationEntity savedNotificationEntity = notificationRepository.save(notificationEntity);
        return NotificationDTOEntityMapper.map(savedNotificationEntity);
    }

    public List<NotificationDTO> getNotificationsByUsername(final String username) {
        if (Objects.isNull(username)) {
            throw new NotificationException("User ID cannot be null");
        }
        final List<NotificationDTO> notificationDTOS = notificationRepository.findAllByUser_Username(username)
                .stream()
                .map(NotificationDTOEntityMapper::map)
                .toList();
        return notificationDTOS;
    }

    @Transactional(rollbackFor = Exception.class)
    public NotificationDTO markNotificationAsRead(final Integer notificationId) {
        if (Objects.isNull(notificationId)) {
            throw new NotificationException("Notification ID cannot be null");
        }
        final NotificationEntity notificationEntity = notificationRepository.findById(notificationId)
                .orElseThrow(() -> {
                    return new NotificationException("Notification not found");
                });
        notificationEntity.setIsRead(true);
        final NotificationEntity updatedNotificationEntity = notificationRepository.save(notificationEntity);
        return NotificationDTOEntityMapper.map(updatedNotificationEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public NotificationDTO deleteNotification(final Integer notificationId) {
        if (Objects.isNull(notificationId)) {
            throw new NotificationException("Notification ID cannot be null");
        }
        final NotificationEntity notificationEntity = notificationRepository.findById(notificationId)
                .orElseThrow(() -> {
                    log.error("Notification with ID {} not found", notificationId);
                    return new NotificationException("Notification not found");
                });
        notificationRepository.delete(notificationEntity);
        return NotificationDTOEntityMapper.map(notificationEntity);
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    public void createAnnouncementNotification(final AnnouncementRequestDTO announcementRequestDTO) {
        if (Objects.isNull(announcementRequestDTO) || announcementRequestDTO.getMessage().isBlank()) {
            throw new NotificationException("Announcement details cannot be null or blank");
        }
        final List<UserEntity> userEntities = userRepository.findAllByUserRole(Constants.UserRoles.CUSTOMER);
        if (userEntities.isEmpty()) {
            throw new NotificationException("No users found for announcement");
        }
        final List<NotificationEntity> notificationsToSave = userEntities.stream()
                .map(user -> {
                    final NotificationEntity notification = new NotificationEntity();
                    notification.setUser(user);
                    notification.setTitle(announcementRequestDTO.getTitle());
                    notification.setMessage(announcementRequestDTO.getMessage());
                    notification.setNotificationType(announcementRequestDTO.getNotificationType());
                    return notification;
                })
                .collect(Collectors.toList());
        notificationRepository.saveAll(notificationsToSave);
    }
}
