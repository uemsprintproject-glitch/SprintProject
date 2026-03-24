package com.sprint.SocialMediaApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sprint.SocialMediaApp.entity.Friend;
import com.sprint.SocialMediaApp.entity.User;
import com.sprint.SocialMediaApp.service.FriendService;
import com.sprint.SocialMediaApp.service.UserService;

import lombok.RequiredArgsConstructor;

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
}
