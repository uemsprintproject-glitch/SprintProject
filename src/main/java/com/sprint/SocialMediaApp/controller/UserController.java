package com.sprint.SocialMediaApp.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sprint.SocialMediaApp.entity.User;
import com.sprint.SocialMediaApp.service.PostService;
import com.sprint.SocialMediaApp.service.UserService;


@Controller
@RequestMapping("/member/users")
public class UserController {

    private final UserService service;

    private final PostService postService;

    public UserController(UserService service, PostService postService) {
        this.service = service;
        this.postService = postService;
    }

    @GetMapping
    public String page() {
        return "users/users";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("data", service.getAllUsers());
        return "/users/result";
    }

    @GetMapping("/by-id")
    public String getById(@RequestParam int id, Model model) {
        model.addAttribute("data", service.getUserById(id));
        return "/users/result";
    }

    @PostMapping("/create")
    public String create(User user) {
        service.createUser(user);
        return "redirect:/member/users";
    }

    @PostMapping("/update")
        public String update(@ModelAttribute User user) {
        service.updateUser(user);
        return "redirect:/member/users";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        service.deleteUser(id);
        return "redirect:/member/users";
    }

    @GetMapping("/posts")
    public String getPostsByUser(@RequestParam int id, Model model) {
        model.addAttribute("posts", postService.getPostsByUser(id));
        return "/users/posts";
    }
}