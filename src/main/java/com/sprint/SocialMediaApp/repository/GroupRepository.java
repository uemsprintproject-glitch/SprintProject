package com.sprint.SocialMediaApp.repository;

import com.sprint.SocialMediaApp.entity.Group;
import com.sprint.SocialMediaApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    List<Group> findByAdmin(User admin);

    Optional<Group> findByGroupName(String groupName);
}
