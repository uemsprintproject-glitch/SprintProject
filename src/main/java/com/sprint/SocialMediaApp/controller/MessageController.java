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
        return "messages";
    }

    @GetMapping("/all")
    public String getAllMessages(Model model) {

        List<Message> messages = messageService.getAllMessages();

        if (messages.isEmpty()) {
            model.addAttribute("errorMessage", "No messages found.");
            return "error";
        }

        model.addAttribute("data", messages);
        return "message-result";
    }

    @GetMapping("/by-id")
    public String getMessageById(@RequestParam Integer id, Model model) {

        Message message = messageService.getMessageByIdSafe(id);

        if (message == null) {
            model.addAttribute("errorMessage", "No message found with ID: " + id);
            return "error";
        }

        model.addAttribute("data", List.of(message));
        return "message-result";
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam Integer senderID,
            @RequestParam Integer receiverID,
            @RequestParam String message_text,
            Model model) {

        User sender = userService.getUserById(senderID);
        User receiver = userService.getUserById(receiverID);

        if (sender == null || receiver == null) {
            model.addAttribute("errorMessage", "Invalid sender or receiver ID.");
            return "error";
        }

        Message message = Message.builder()
                .message_text(message_text)
                .sender(sender)
                .receiver(receiver)
                .build();

        messageService.sendMessage(message);

        return "redirect:/members/messages";
    }

    @GetMapping("/delete")
    public String deleteMessage(@RequestParam Integer id, Model model) {

        boolean deleted = messageService.deleteMessageSafe(id);

        if (!deleted) {
            model.addAttribute("errorMessage", "Cannot delete. Message not found with ID: " + id);
            return "error";
        }

        return "redirect:/members/messages";
    }
}