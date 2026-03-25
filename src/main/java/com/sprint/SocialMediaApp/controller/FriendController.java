package com.sprint.SocialMediaApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sprint.SocialMediaApp.entity.Friend;
import com.sprint.SocialMediaApp.entity.User;
import com.sprint.SocialMediaApp.service.FriendService;
import com.sprint.SocialMediaApp.service.UserService;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;

    @GetMapping
    public String getAllFriends(Model model) {
        model.addAttribute("data", friendService.getAllFriends());
        model.addAttribute("friend", new Friend());
        return "friends";
    }

    @GetMapping("/by-user")
    public String getFriendsByUser(@RequestParam int userID, Model model) {
        User user = userService.getUserById(userID);
        model.addAttribute("data", friendService.getFriendsOfUser(user));
        model.addAttribute("userID", userID);
        return "friends/result";
    }

    @GetMapping("/requests")
    public String getFriendRequests(@RequestParam int userID, Model model) {
        User user = userService.getUserById(userID);
        model.addAttribute("data", friendService.getFriendRequests(user));
        model.addAttribute("userID", userID);
        return "friends/requests";
    }

    @GetMapping("/accepted")
    public String getAcceptedFriends(@RequestParam int userID, Model model) {
        User user = userService.getUserById(userID);
        model.addAttribute("data", friendService.getAcceptedFriends(user));
        model.addAttribute("userID", userID);
        return "friends/accepted";
    }

    @PostMapping("/send-request")
    public String sendFriendRequest(@RequestParam int user1ID,
            @RequestParam int user2ID) {

        User user1 = userService.getUserById(user1ID);
        User user2 = userService.getUserById(user2ID);

        Friend friend = Friend.builder()
                .user1(user1)
                .user2(user2)
                .status(Friend.Status.pending)
                .build();

        friendService.sendFriendRequest(friend);

        return "redirect:/friends/by-user?userID=" + user1ID;
    }

    @PostMapping("/accept")
    public String acceptFriendRequest(@RequestParam int friendshipID,
            @RequestParam int userID) {

        friendService.acceptFriendRequest(friendshipID);

        return "redirect:/friends/requests?userID=" + userID;
    }

    @GetMapping("/delete/{id}")
    public String deleteFriend(@PathVariable int id,
            @RequestParam int userID) {

        friendService.deleteFriend(id);

        return "redirect:/friends/by-user?userID=" + userID;
    }

    @GetMapping("/view/{id}")
    public String viewFriend(@PathVariable int id, Model model) {
        Friend friend = friendService.getFriendById(id);
        model.addAttribute("friend", friend);
        return "friends/view";
    }

    @GetMapping("/api/friends/{userId}")
    @ResponseBody
    public ResponseEntity<?> apiFriendsOfUser(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }

        List<Friend> friends = friendService.getAcceptedFriends(user);
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/api/friends/requests/{userId}")
    @ResponseBody
    public ResponseEntity<?> apiFriendRequests(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }

        List<Friend> requests = friendService.getFriendRequests(user);
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/api/friends/request")
    @ResponseBody
    public ResponseEntity<?> apiSendFriendRequest(@RequestBody Map<String, Integer> payload) {
        Integer user1ID = payload.get("user1ID");
        Integer user2ID = payload.get("user2ID");

        if (user1ID == null || user2ID == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "user1ID and user2ID are required"));
        }

        if (user1ID.equals(user2ID)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Cannot send friend request to yourself"));
        }

        User user1 = userService.getUserById(user1ID);
        User user2 = userService.getUserById(user2ID);

        if (user1 == null || user2 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "One or both users not found"));
        }

        Friend existingFriendship = friendService.findExistingFriendship(user1, user2);
        if (existingFriendship != null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Friend request already exists"));
        }

        Friend friend = Friend.builder()
                .user1(user1)
                .user2(user2)
                .status(Friend.Status.pending)
                .build();

        friendService.sendFriendRequest(friend);

        return ResponseEntity.status(HttpStatus.CREATED).body(friend);
    }

    @PutMapping("/api/friends/accept/{id}")
    @ResponseBody
    public ResponseEntity<?> apiAcceptFriendRequest(@PathVariable Integer id) {
        Friend friendship = friendService.getFriendById(id);

        if (friendship == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Friendship request not found"));
        }

        if (friendship.getStatus() == Friend.Status.accepted) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Friendship already accepted"));
        }

        friendService.acceptFriendRequest(id);
        return ResponseEntity.ok(Map.of("message", "Friend request accepted"));
    }

    @DeleteMapping("/api/friends/{id}")
    @ResponseBody
    public ResponseEntity<?> apiDeleteFriend(@PathVariable Integer id) {
        Friend friendship = friendService.getFriendById(id);

        if (friendship == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Friendship not found"));
        }

        friendService.deleteFriend(id);
        return ResponseEntity.ok(Map.of("message", "Friend removed successfully"));
    }

    @GetMapping("/api/friends/sent/{userId}")
    @ResponseBody
    public ResponseEntity<?> apiSentFriendRequests(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }

        List<Friend> sentRequests = friendService.getSentFriendRequests(user);
        return ResponseEntity.ok(sentRequests);
    }

    @GetMapping("/api/friends/{friendshipId}/status")
    @ResponseBody
    public ResponseEntity<?> apiFriendshipStatus(@PathVariable Integer friendshipId) {
        Friend friendship = friendService.getFriendById(friendshipId);

        if (friendship == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Friendship not found"));
        }

        return ResponseEntity.ok(Map.of(
                "friendshipID", friendship.getFriendshipID(),
                "user1ID", friendship.getUser1().getUserID(),
                "user2ID", friendship.getUser2().getUserID(),
                "status", friendship.getStatus()
        ));
    }
}
