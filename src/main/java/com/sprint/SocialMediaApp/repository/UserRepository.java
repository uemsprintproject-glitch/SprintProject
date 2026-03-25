package com.sprint.SocialMediaApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprint.SocialMediaApp.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    
}
