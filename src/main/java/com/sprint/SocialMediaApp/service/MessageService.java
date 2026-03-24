package com.sprint.SocialMediaApp.service;

import com.sprint.SocialMediaApp.entity.Message;
import com.sprint.SocialMediaApp.entity.User;
import com.sprint.SocialMediaApp.repository.MessageRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Message sendMessage(Message message) {
        message.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
    }

    public List<Message> getMessagesBySender(User sender) {
        return messageRepository.findBySender(sender);
    }

    public List<Message> getMessagesByReceiver(User receiver) {
        return messageRepository.findByReceiver(receiver);
    }

    public Message updateMessage(Integer id, Message updatedMessage) {
        Message message = getMessageById(id);

        message.setMessage_text(updatedMessage.getMessage_text());
        message.setSender(updatedMessage.getSender());
        message.setReceiver(updatedMessage.getReceiver());
        message.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        return messageRepository.save(message);
    }

    public void deleteMessage(Integer id) {
        messageRepository.deleteById(id);
    }
}