package com.sprint.SocialMediaApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageID;

    private String message_text;

    private java.sql.Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "senderID")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiverID")
    private User receiver;
}
