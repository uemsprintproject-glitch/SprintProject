package com.sprint.SocialMediaApp.controller;

import com.sprint.SocialMediaApp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/home")
    public String getHomePage(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "home"; // home.html
    }

    @PostMapping("/posts")
    public String createPost(@RequestParam Integer userId,
                             @RequestParam String content) {

        postService.createPost(userId, content);

        return "redirect:/home";
    }

    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return "redirect:/home";
    }
}
