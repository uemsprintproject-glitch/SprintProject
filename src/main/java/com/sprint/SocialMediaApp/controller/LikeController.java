package com.sprint.SocialMediaApp.controller;

import com.sprint.SocialMediaApp.entity.Like;
import com.sprint.SocialMediaApp.entity.Post;
import com.sprint.SocialMediaApp.entity.User;
import com.sprint.SocialMediaApp.service.LikeService;
import com.sprint.SocialMediaApp.service.PostService;
import com.sprint.SocialMediaApp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/member/likes")
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;
    private final PostService postService;

    public LikeController(LikeService likeService, UserService userService, PostService postService) {
        this.likeService = likeService;
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping
    public String page() {
        return "like/likes";
    }

    @GetMapping("/all")
    public String getAllLikes(Model model) {
        model.addAttribute("data", likeService.getAllLikes());
        return "like/result";
    }

    @GetMapping("/by-id")
    public String getById(@RequestParam Integer id, Model model) {
        model.addAttribute("data", List.of(likeService.getLikeById(id)));
        return "like/result";
    }

    @GetMapping("/by-user")
    public String getByUser(@RequestParam Integer userID, Model model) {
        model.addAttribute("data", likeService.getLikesByUser(userID));
        return "like/result";
    }

    @GetMapping("/by-post")
    public String getByPost(@RequestParam Integer postID, Model model) {
        model.addAttribute("data", likeService.getLikesByPost(postID));
        return "like/result";
    }

    @PostMapping("/create")
    public String create(@RequestParam Integer likeID,
            @RequestParam Integer userID,
            @RequestParam Integer postID) {

        User user = userService.getUserById(userID);
        Post post = postService.getPostById(postID);

        Like like = Like.builder()
                .likeID(likeID)
                .user(user)
                .post(post)
                .build();

        likeService.createLike(like);
        return "redirect:/member/likes";
    }

    @PostMapping("/update")
    public String update(@RequestParam Integer likeID,
            @RequestParam Integer userID,
            @RequestParam Integer postID) {

        User user = userService.getUserById(userID);
        Post post = postService.getPostById(postID);

        Like updatedLike = Like.builder()
                .likeID(likeID)
                .user(user)
                .post(post)
                .build();

        likeService.updateLike(updatedLike);
        return "redirect:/member/likes";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer id) {
        likeService.deleteLike(id);
        return "redirect:/member/likes";
    }
}
