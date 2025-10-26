package com.bankingapp.root.dto;

import com.bankingapp.root.common.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class NotificationDTO {

    private Integer notificationId;

    @NotBlank(message = "User ID must not be blank")
    @NonNull
    private String username;

    @NonNull
    @NotBlank(message = "Title must not be blank")
    private String title;

    @NonNull
    @NotNull(message = "Notification type must not be null")
    private Constants.NotificationType notificationType;

    @NonNull
    @NotBlank(message = "Message must not be blank")
    private String message;

    private Boolean isRead = false;

    private LocalDateTime createdAt;
}
