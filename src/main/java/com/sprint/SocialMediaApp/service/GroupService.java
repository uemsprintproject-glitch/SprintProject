package com.sprint.SocialMediaApp.service;

import org.springframework.stereotype.Service;

import com.sprint.SocialMediaApp.entity.Group;
import com.sprint.SocialMediaApp.entity.User;
import com.sprint.SocialMediaApp.repository.GroupRepository;
import com.sprint.SocialMediaApp.repository.UserRepository;

import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepo;
    private final UserRepository userRepo;

    public GroupService(GroupRepository groupRepo, UserRepository userRepo) {
        this.groupRepo = groupRepo;
        this.userRepo = userRepo;
    }

    public List<Group> getAllGroups() {
        return groupRepo.findAll();
    }

    public Group getGroupById(int id) {
        return groupRepo.findById(id).orElse(null);
    }

    public Group getGroupByName(String groupName) {
        return groupRepo.findByGroupName(groupName).orElse(null);
    }

    public void createGroup(Group group) {
        groupRepo.save(group);
    }

    public void updateGroup(Group updatedGroup) {
        Group existingGroup = groupRepo.findById(updatedGroup.getGroupID())
            .orElseThrow();

        existingGroup.setGroupName(updatedGroup.getGroupName());
        existingGroup.setAdmin(updatedGroup.getAdmin());

        groupRepo.save(existingGroup);
    }

    public void deleteGroup(int id) {
        groupRepo.deleteById(id);
    }

    public List<Group> getGroupsByAdmin(int adminId) {
        User admin = userRepo.findById(adminId).orElse(null);
        if (admin == null) {
            return null;
        }
        return groupRepo.findByAdmin(admin);
    }
}
