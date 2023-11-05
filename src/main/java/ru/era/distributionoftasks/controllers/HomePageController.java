package ru.era.distributionoftasks.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomePageController {
    @GetMapping("/userpage")
    public String userPageAuth() {
        return "userpage";
    }

    @GetMapping("/managerpage")
    public String managerPageAuth(@AuthenticationPrincipal User user) {
        return "managerpage";
    }
}
