package com.sprint.SocialMediaApp.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @Id
    private Integer groupID;

    private String groupName;

    @ManyToOne
    @JoinColumn(name = "adminID")
    private User admin;
}
