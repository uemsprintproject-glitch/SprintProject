package com.sprint.SocialMediaApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sprint.SocialMediaApp.entity.Comment;
import com.sprint.SocialMediaApp.entity.Post;
import com.sprint.SocialMediaApp.entity.User;
import com.sprint.SocialMediaApp.service.CommentService;
import com.sprint.SocialMediaApp.service.PostService;
import com.sprint.SocialMediaApp.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @GetMapping
    public String getAllComments(Model model) {
        model.addAttribute("data", commentService.getAllComments());
        model.addAttribute("comment", new Comment());
        return "comments";
    }

    @GetMapping("/by-post")
    public String getCommentsByPost(@RequestParam int postID, Model model) {
        model.addAttribute("data", commentService.getCommentsByPost(postID));
        model.addAttribute("postID", postID);
        return "comments/result";
    }

    @PostMapping("/create")
    public String createComment(@RequestParam int postID,
            @RequestParam int userID,
            @RequestParam String comment_text) {

        User user = userService.getUserById(userID);

        Comment comment = Comment.builder()
                .comment_text(comment_text)
                .user(user)
                .timestamp(new java.sql.Timestamp(System.currentTimeMillis()))
                .build();

        commentService.addComment(comment);

        return "redirect:/comments/by-post?postID=" + postID;
    }

    @PostMapping("/update")
    public String updateComment(@RequestParam int commentID,
            @RequestParam int postID,
            @RequestParam String comment_text) {

        commentService.updateComment(commentID, comment_text);

        return "redirect:/comments/by-post?postID=" + postID;
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable int id,
            @RequestParam int postID) {

        commentService.deleteComment(id);

        return "redirect:/comments/by-post?postID=" + postID;
    }

    @GetMapping("/edit/{id}")
    public String editComment(@PathVariable int id, Model model) {
        Comment comment = commentService.getCommentById(id);
        model.addAttribute("comment", comment);
        return "comments/edit";
    }
}
