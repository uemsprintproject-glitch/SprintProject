package com.sprint.SocialMediaApp.repository;

import com.sprint.SocialMediaApp.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

    List<Like> findByUserUserID(Integer userID);

    List<Like> findByPostPostID(Integer postID);
}
