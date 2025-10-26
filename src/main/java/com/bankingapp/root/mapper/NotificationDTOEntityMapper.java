package com.bankingapp.root.mapper;

import com.bankingapp.root.dto.NotificationDTO;
import com.bankingapp.root.entity.NotificationEntity;

import java.util.Objects;

public class NotificationDTOEntityMapper {

    public static NotificationDTO map(NotificationEntity notificationEntity) {
        if (Objects.isNull(notificationEntity))
            throw new IllegalArgumentException("Notification entity is null");
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setNotificationId(notificationEntity.getNotificationId());
        if(Objects.isNull(notificationEntity.getUser()))
            throw new IllegalArgumentException("User in notification entity is null");
        notificationDTO.setUsername(notificationEntity.getUser().getUsername());
        notificationDTO.setMessage(notificationEntity.getMessage());
        notificationDTO.setCreatedAt(notificationEntity.getCreatedAt());
        notificationDTO.setNotificationType(notificationEntity.getNotificationType());
        notificationDTO.setIsRead(notificationEntity.getIsRead());
        notificationDTO.setTitle(notificationEntity.getTitle());
        return notificationDTO;
    }

    public static NotificationEntity map(NotificationDTO notificationDTO) {
        if (Objects.isNull(notificationDTO))
            throw new IllegalArgumentException("Notification DTO is null");
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setNotificationId(notificationDTO.getNotificationId());
        notificationEntity.setMessage(notificationDTO.getMessage());
        notificationEntity.setCreatedAt(notificationDTO.getCreatedAt());
        notificationEntity.setIsRead(notificationDTO.getIsRead());
        notificationEntity.setTitle(notificationDTO.getTitle());
        notificationEntity.setNotificationType(notificationDTO.getNotificationType());
        return notificationEntity;
    }
}
