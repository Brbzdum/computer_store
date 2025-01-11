package ru.xdd.computer_store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("content", "Здесь будет ваш контент"); // или передайте контент из других переменных
        return "home";
    }


}

