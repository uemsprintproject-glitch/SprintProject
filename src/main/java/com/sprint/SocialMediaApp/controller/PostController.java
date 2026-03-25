package com.sprint.SocialMediaApp.controller;

import com.sprint.SocialMediaApp.service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/members/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping
    public String page() {
        return "posts/post";
    }

    @GetMapping("/all")
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "posts/result";
    }

    @GetMapping("/by-user")
    public String getPostsByUser(@RequestParam Integer userId, Model model) {
        model.addAttribute("posts", postService.getPostsByUser(userId));
        return "posts/result";
    }

    @PostMapping("/create")
    public String createPost(@RequestParam Integer userId,
                             @RequestParam String content) {
        postService.createPost(userId, content);

        return "posts/post";
    }

    @GetMapping("/delete")
    public String deletePost(@RequestParam Integer id) {
        postService.deletePost(id);
        return "redirect:/members/posts";
    }
}