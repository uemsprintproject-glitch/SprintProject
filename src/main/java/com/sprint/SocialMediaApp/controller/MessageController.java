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
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @GetMapping
    public String getAllMessages(Model model) {
        List<Message> messages = messageService.getAllMessages();
        model.addAttribute("messages", messages);
        model.addAttribute("message", new Message());
        return "messages";
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

        return "redirect:/messages";
    }

    @GetMapping("/delete/{id}")
    public String deleteMessage(@PathVariable Integer id) {
        messageService.deleteMessage(id);
        return "redirect:/messages";
    }

    @GetMapping("/edit/{id}")
    public String editMessage(@PathVariable Integer id, Model model) {
        Message message = messageService.getMessageById(id);
        model.addAttribute("message", message);
        return "edit-message";
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

        return "redirect:/messages";
    }
}