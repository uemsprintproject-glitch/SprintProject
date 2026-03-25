package com.sprint.SocialMediaApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sprint.SocialMediaApp.entity.Group;
import com.sprint.SocialMediaApp.service.GroupService;

@Controller
@RequestMapping("/member/groups")
public class GroupController {

    private final GroupService service;

    public GroupController(GroupService service) {
        this.service = service;
    }

    @GetMapping
    public String page() {
        return "group/groups";
    }

    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("data", service.getAllGroups());
        return "group/result";
    }

    @GetMapping("/by-id")
    public String getById(@RequestParam int id, Model model) {
        model.addAttribute("data", java.util.List.of(service.getGroupById(id)));
        return "group/result";
    }

    @GetMapping("/by-name")
    public String getByName(@RequestParam String groupName, Model model) {
        model.addAttribute("data", java.util.List.of(service.getGroupByName(groupName)));
        return "group/result";
    }

    @PostMapping("/create")
    public String create(Group group) {
        service.createGroup(group);
        return "redirect:/member/groups";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Group group) {
        service.updateGroup(group);
        return "redirect:/member/groups";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        service.deleteGroup(id);
        return "redirect:/member/groups";
    }

    @GetMapping("/by-admin")
    public String getGroupsByAdmin(@RequestParam int adminId, Model model) {
        model.addAttribute("data", service.getGroupsByAdmin(adminId));
        return "group/result";
    }
}
