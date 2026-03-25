package com.sprint.SocialMediaApp.controller;

import com.sprint.SocialMediaApp.entity.Notification;
import com.sprint.SocialMediaApp.entity.User;
import com.sprint.SocialMediaApp.service.NotificationService;
import com.sprint.SocialMediaApp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping
    public String page() {
        return "notification/notifications";
    }

    @GetMapping("/all")
    public String getAllNotifications(Model model) {
        model.addAttribute("data", notificationService.getAllNotifications());
        return "notification/result";
    }

    @GetMapping("/by-id")
    public String getById(@RequestParam Integer id, Model model) {
        model.addAttribute("data", List.of(notificationService.getNotificationById(id)));
        return "notification/result";
    }

    @GetMapping("/by-user")
    public String getByUser(@RequestParam Integer userID, Model model) {
        model.addAttribute("data", notificationService.getNotificationsByUser(userID));
        return "notification/result";
    }

    @PostMapping("/create")
    public String create(@RequestParam Integer notificationID,
            @RequestParam String content,
            @RequestParam Integer userID) {

        User user = userService.getUserById(userID);

        Notification notification = Notification.builder()
                .notificationID(notificationID)
                .content(content)
                .user(user)
                .build();

        notificationService.createNotification(notification);
        return "redirect:/member/notifications";
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer notificationID,
            @RequestParam String content,
            @RequestParam Integer userID) {

        User user = userService.getUserById(userID);

        Notification updatedNotification = Notification.builder()
                .notificationID(notificationID)
                .content(content)
                .user(user)
                .build();

        notificationService.updateNotification(updatedNotification);
        return "redirect:/member/notifications";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id) {
        notificationService.deleteNotification(id);
        return "redirect:/member/notifications";
    }
}
