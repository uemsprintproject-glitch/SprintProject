package com.sprint.SocialMediaApp.controller;

import com.sprint.SocialMediaApp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public String addComment(@RequestParam Integer userId,
                             @RequestParam Integer postId,
                             @RequestParam String text) {

        commentService.addComment(userId, postId, text);

        return "redirect:/home";
    }
        @GetMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return "redirect:/home";
    }
}