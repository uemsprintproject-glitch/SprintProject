package com.sprint.SocialMediaApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sprint.SocialMediaApp.entity.Friend;
import com.sprint.SocialMediaApp.entity.User;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {

    List<Friend> findByUser1(User user1);
    
    List<Friend> findByUser2(User user2);
    
    List<Friend> findByStatus(Friend.Status status);
    
    List<Friend> findByUser1AndStatus(User user1, Friend.Status status);
    
    List<Friend> findByUser2AndStatus(User user2, Friend.Status status);
    
    Friend findByUser1AndUser2(User user1, User user2);
}
