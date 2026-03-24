package com.sprint.SocialMediaApp.service;
import org.springframework.stereotype.Service;

import com.sprint.SocialMediaApp.entity.User;
import com.sprint.SocialMediaApp.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public User getUserById(int id) {
        return repo.findById(id).orElse(null);
    }

    public void createUser(User user) {
        repo.save(user);
    }

    public void updateUser(User updatedUser) {
        User existingUser = repo.findById(updatedUser.getUserID())
            .orElseThrow();

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setProfilePicture(updatedUser.getProfilePicture());

        repo.save(existingUser);
    }

    public void deleteUser(int id) {
        repo.deleteById(id);
    }
}