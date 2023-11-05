package ru.era.distributionoftasks.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePageController {
    @GetMapping("/userpage")
    @ResponseBody
    public String userPageAuth(@AuthenticationPrincipal User user) {
        return user.getUsername();
    }

    @GetMapping("/managerpage")
    @ResponseBody
    public String managerPageAuth(@AuthenticationPrincipal User user) {
        return user.getUsername();
    }
}
