package com.sprint.SocialMediaApp.service;
import java.util.List;

import org.springframework.stereotype.Service;
import com.sprint.SocialMediaApp.entity.Post;
import com.sprint.SocialMediaApp.repository.PostRepository;

@Service
public class PostService {

    private PostRepository postRepo;

    public PostService(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    public List<Post> getPostsByUser(int userid) {
        return postRepo.findByUserUserID(userid);
    }
}
