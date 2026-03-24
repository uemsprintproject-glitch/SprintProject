package com.sprint.SocialMediaApp.controller;

import com.sprint.SocialMediaApp.entity.Like;
import com.sprint.SocialMediaApp.entity.Post;
import com.sprint.SocialMediaApp.entity.User;
import com.sprint.SocialMediaApp.service.LikeService;
import com.sprint.SocialMediaApp.service.PostService;
import com.sprint.SocialMediaApp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @GetMapping("/all")
    public List<Like> getAllLikes() {
        return likeService.getAllLikes();
    }

    @GetMapping("/by-id")
    public Like getById(@RequestParam Integer id) {
        return likeService.getLikeById(id);
    }

    @GetMapping("/by-user")
    public List<Like> getByUser(@RequestParam Integer userID) {
        return likeService.getLikesByUser(userID);
    }

    @GetMapping("/by-post")
    public List<Like> getByPost(@RequestParam Integer postID) {
        return likeService.getLikesByPost(postID);
    }

    @PostMapping("/create")
    public Like create(@RequestParam Integer likeID,
            @RequestParam Integer userID,
            @RequestParam Integer postID) {

        User user = userService.getUserById(userID);
        Post post = postService.getPostById(postID);

        Like like = Like.builder()
                .likeID(likeID)
                .user(user)
                .post(post)
                .build();

        return likeService.createLike(like);
    }

    @PostMapping("/update")
    public Like update(@RequestParam Integer likeID,
            @RequestParam Integer userID,
            @RequestParam Integer postID) {

        User user = userService.getUserById(userID);
        Post post = postService.getPostById(postID);

        Like updatedLike = Like.builder()
                .likeID(likeID)
                .user(user)
                .post(post)
                .build();

        return likeService.updateLike(updatedLike);
    }

    @PostMapping("/delete")
    public void delete(@RequestParam Integer id) {
        likeService.deleteLike(id);
    }
}
