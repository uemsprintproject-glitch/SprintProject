package com.sprint.SocialMediaApp.service;

import com.sprint.SocialMediaApp.entity.Post;
import com.sprint.SocialMediaApp.entity.User;
import com.sprint.SocialMediaApp.exception.ResourceNotFoundException;
import com.sprint.SocialMediaApp.repository.PostRepository;
import com.sprint.SocialMediaApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post createPost(Integer userId, String content) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Post post = Post.builder()
                .content(content)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .user(user)
                .build();

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByTimestampDesc();
    }

    public List<Post> getPostsByUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return postRepository.findByUser(user);
    }

    public void deletePost(Integer postId) {
        postRepository.deleteById(postId);
    }
}