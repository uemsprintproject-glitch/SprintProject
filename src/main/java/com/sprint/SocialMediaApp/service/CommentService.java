package com.sprint.SocialMediaApp.service;

import org.springframework.stereotype.Service;
import com.sprint.SocialMediaApp.entity.Comment;
import com.sprint.SocialMediaApp.repository.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(Integer id) {
        return commentRepository.findById(id).orElse(null);
    }

    public List<Comment> getCommentsByPost(Integer postId) {
        return commentRepository.findByPostPostID(postId);
    }

    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void updateComment(Integer commentId, String comment_text) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow();

        existingComment.setComment_text(comment_text);
        existingComment.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));

        commentRepository.save(existingComment);
    }

    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }
}
