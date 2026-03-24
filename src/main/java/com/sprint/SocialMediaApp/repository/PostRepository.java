package com.sprint.SocialMediaApp.repository;

<<<<<<< HEAD
import com.sprint.SocialMediaApp.entity.Post;
import com.sprint.SocialMediaApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);

    List<Post> findAllByOrderByTimestampDesc();
}
=======
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sprint.SocialMediaApp.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUserUserID(int userId);
}
>>>>>>> origin/feature-ashmit
