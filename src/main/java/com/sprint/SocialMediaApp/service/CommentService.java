package com.sprint.SocialMediaApp.service;

import com.sprint.SocialMediaApp.entity.Comment;
import com.sprint.SocialMediaApp.entity.Post;
import com.sprint.SocialMediaApp.entity.User;
import com.sprint.SocialMediaApp.repository.CommentRepository;
import com.sprint.SocialMediaApp.repository.PostRepository;
import com.sprint.SocialMediaApp.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Comment addComment(Integer userId, Integer postId, String text) {

        if (text == null || text.trim().isEmpty()) {
            throw new RuntimeException("Comment cannot be empty");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = Comment.builder()
                .comment_text(text)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .user(user)
                .post(post)
                .build();

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return commentRepository.findByPost(post);
    }

    public void deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
    }
}