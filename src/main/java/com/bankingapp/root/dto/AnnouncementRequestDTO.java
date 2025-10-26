package com.bankingapp.root.dto;

import com.bankingapp.root.common.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementRequestDTO {

    @JsonProperty("title")
    @NotBlank(message = "Announcement title cannot be blank")
    private String title;

    @JsonProperty("notification_type")
    private Constants.NotificationType notificationType = Constants.NotificationType.ANNOUNCEMENT;

    @JsonProperty("message")
    @NotBlank(message = "Announcement message cannot be blank")
    private String message;
}
