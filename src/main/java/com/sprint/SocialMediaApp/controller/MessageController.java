package com.sprint.SocialMediaApp.controller;

import com.sprint.SocialMediaApp.entity.Message;
import com.sprint.SocialMediaApp.entity.User;
import com.sprint.SocialMediaApp.service.MessageService;
import com.sprint.SocialMediaApp.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @GetMapping
    public String messagePage() {
        return "messages/messages";
    }

    @GetMapping("/all")
    public String getAllMessages(Model model) {
        model.addAttribute("data", messageService.getAllMessages());
        return "messages/result";
    }

    @GetMapping("/by-id")
    public String getMessageById(@RequestParam Integer id, Model model) {
        model.addAttribute("data",
                List.of(messageService.getMessageById(id)));
        return "messages/result";
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam Integer messageID,
            @RequestParam Integer senderID,
            @RequestParam Integer receiverID,
            @RequestParam String message_text) {

        User sender = userService.getUserById(senderID);
        User receiver = userService.getUserById(receiverID);

        Message message = Message.builder()
                .messageID(messageID)
                .message_text(message_text)
                .sender(sender)
                .receiver(receiver)
                .build();

        messageService.sendMessage(message);

        return "redirect:/members/messages";
    }

    @PostMapping("/update")
    public String updateMessage(@RequestParam Integer messageID,
            @RequestParam Integer senderID,
            @RequestParam Integer receiverID,
            @RequestParam String message_text) {

        User sender = userService.getUserById(senderID);
        User receiver = userService.getUserById(receiverID);

        Message updatedMessage = Message.builder()
                .messageID(messageID)
                .message_text(message_text)
                .sender(sender)
                .receiver(receiver)
                .build();

        messageService.updateMessage(messageID, updatedMessage);

        return "redirect:/members/messages";
    }

    @GetMapping("/delete")
    public String deleteMessage(@RequestParam Integer id) {
        messageService.deleteMessage(id);
        return "redirect:/members/messages";
    }
}