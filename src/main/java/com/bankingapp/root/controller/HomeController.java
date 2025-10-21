package com.bankingapp.root.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/")
    public String HomePage(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        if (role != null) {
            model.addAttribute("dashboardLink", "/" + role.toLowerCase() + "/dashboard");
        }
        return "index";
    }
}
