package com.sprint.SocialMediaApp.repository;

import com.sprint.SocialMediaApp.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByUserUserID(Integer userID);
}
