package com.sprint.SocialMediaApp.service;

import org.springframework.stereotype.Service;
import com.sprint.SocialMediaApp.entity.Friend;
import com.sprint.SocialMediaApp.entity.User;
import com.sprint.SocialMediaApp.repository.FriendRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;

    public List<Friend> getAllFriends() {
        return friendRepository.findAll();
    }

    public Friend getFriendById(Integer id) {
        return friendRepository.findById(id).orElse(null);
    }

    public List<Friend> getFriendsOfUser(User user) {
        return friendRepository.findByUser1(user);
    }

    public List<Friend> getFriendRequests(User user) {
        return friendRepository.findByUser2AndStatus(user, Friend.Status.pending);
    }

    public List<Friend> getAcceptedFriends(User user) {
        return friendRepository.findByUser1AndStatus(user, Friend.Status.accepted);
    }

    public void sendFriendRequest(Friend friend) {
        friend.setStatus(Friend.Status.pending);
        friendRepository.save(friend);
    }

    public void acceptFriendRequest(Integer friendshipId) {
        Friend existingFriend = friendRepository.findById(friendshipId)
                .orElseThrow();

        existingFriend.setStatus(Friend.Status.accepted);
        friendRepository.save(existingFriend);
    }

    public void updateFriend(Friend friend) {
        Friend existingFriend = friendRepository.findById(friend.getFriendshipID())
                .orElseThrow();

        existingFriend.setStatus(friend.getStatus());
        friendRepository.save(existingFriend);
    }

    public void deleteFriend(Integer id) {
        friendRepository.deleteById(id);
    }
}
