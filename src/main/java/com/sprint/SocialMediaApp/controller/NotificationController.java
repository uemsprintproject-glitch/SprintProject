package com.sprint.SocialMediaApp.controller;

import com.sprint.SocialMediaApp.entity.Notification;
import com.sprint.SocialMediaApp.entity.User;
import com.sprint.SocialMediaApp.service.NotificationService;
import com.sprint.SocialMediaApp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/by-id")
    public Notification getById(@RequestParam Integer id) {
        return notificationService.getNotificationById(id);
    }

    @GetMapping("/by-user")
    public List<Notification> getByUser(@RequestParam Integer userID) {
        return notificationService.getNotificationsByUser(userID);
    }

    @PostMapping("/create")
    public Notification create(@RequestParam Integer notificationID,
            @RequestParam String content,
            @RequestParam Integer userID) {

        User user = userService.getUserById(userID);

        Notification notification = Notification.builder()
                .notificationID(notificationID)
                .content(content)
                .user(user)
                .build();

        return notificationService.createNotification(notification);
    }

    @PostMapping("/update")
    public Notification update(@RequestParam Integer notificationID,
            @RequestParam String content,
            @RequestParam Integer userID) {

        User user = userService.getUserById(userID);

        Notification updatedNotification = Notification.builder()
                .notificationID(notificationID)
                .content(content)
                .user(user)
                .build();

        return notificationService.updateNotification(updatedNotification);
    }

    @PostMapping("/delete")
    public void delete(@RequestParam Integer id) {
        notificationService.deleteNotification(id);
    }
}
