package com.bankingapp.root.controller;

import com.bankingapp.root.dto.AnnouncementRequestDTO;
import com.bankingapp.root.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notification")
@Validated
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/delete/{notificationId}")
    public String deleteNotification(
            @PathVariable Integer notificationId) {
        notificationService.deleteNotification(notificationId);
        return "redirect:/customer/dashboard?notificationDeleted=success";
    }

    @GetMapping( "/read/{notificationId}")
    public String markNotificationAsRead(
            @PathVariable Integer notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return "redirect:/customer/dashboard?notificationUpdated=success";
    }

    @PostMapping(value = "/create/announcement")
    public String createAnnouncement(@ModelAttribute("Announcement") @Valid AnnouncementRequestDTO announcementRequestDTO) {
        notificationService.createAnnouncementNotification(announcementRequestDTO);
        return "redirect:/admin/dashboard?announcementCreated=success";
    }
}
