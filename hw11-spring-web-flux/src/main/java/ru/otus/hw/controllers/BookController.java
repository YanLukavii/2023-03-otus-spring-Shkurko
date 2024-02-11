package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BookController {

    @GetMapping("/")
    public String listPage() {
        return "list";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") String id, Model model) {
        model.addAttribute("id", id);
        return "edit";
    }

    @GetMapping("/create")
    public String createPage() {
        return "create";
    }

    @GetMapping("/comments/{id}")
    public String commentsPage(@PathVariable("id") String id, Model model) {
        model.addAttribute("id", id);
        return "comments";
    }
}