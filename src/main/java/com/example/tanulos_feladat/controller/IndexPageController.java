package com.example.tanulos_feladat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexPageController {
    @GetMapping(
            value = "/books"
    )
    public String indexPage(Model model) {

        return "index";
    }

}
