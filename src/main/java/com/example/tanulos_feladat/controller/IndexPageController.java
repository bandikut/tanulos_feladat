package com.example.tanulos_feladat.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.*;

@Controller
public class IndexPageController {
    @GetMapping(
            value = "/books"
    )
    public String indexPage() {
        return "index";
    }

}
