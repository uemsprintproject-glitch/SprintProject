package com.sprint.SocialMediaApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    private Integer postID;

    private String content;

    private java.sql.Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<Like> likes;
}