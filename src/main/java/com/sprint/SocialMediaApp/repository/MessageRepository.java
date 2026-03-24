package com.sprint.SocialMediaApp.repository;

import com.sprint.SocialMediaApp.entity.Message;
import com.sprint.SocialMediaApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findBySender(User sender);

    List<Message> findByReceiver(User receiver);
}
