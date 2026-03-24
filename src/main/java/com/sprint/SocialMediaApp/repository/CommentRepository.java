package com.sprint.SocialMediaApp.repository;

import com.sprint.SocialMediaApp.entity.Comment;
import com.sprint.SocialMediaApp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findByPost(Post post);

    Comment save(Comment comment);

    void deleteById(Integer commentId);
}
