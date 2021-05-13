package com.example.tanulos_feladat.controller;

import com.example.tanulos_feladat.dto.AuthorDTO;
import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthorController {

    @Autowired
    private MapService mapService;

    @GetMapping(value = "/addauthor")
    public String newAuthor(Model model) {
        model.addAttribute("nauthor", new AuthorDTO());
        return "addauthor";
    }

    @PostMapping(value = "/saveAuthor")
    public String saveAuthor(@ModelAttribute AuthorDTO form, Model model) throws Exception {
        mapService.addAuthor(form);
        return "redirect:/addauthor";
    }

    @RequestMapping(value = "/viewauthor{id}", method = RequestMethod.GET)
    public String authorsBooks(Model model,
                               @RequestParam("id") Long id) {
        AuthorDTO authorDTO = mapService.findAuthorById(id);
        model.addAttribute("author", authorDTO);
        List<BookDTO> books = mapService.getAllBooks();
        model.addAttribute("books", books);
        return "viewauthor";
    }

    @PostMapping(value = "/{deleteauthor}")
    public String deleteAuthor(@RequestParam("deleteauthor") Long id) {
        mapService.deleteAuthor(id);
        System.out.println("id :" + id);
        return "redirect:/allbook";
    }


    @RequestMapping(value = "/viewauthor/update{id}", method = RequestMethod.POST)
    public String updateAuthor(@ModelAttribute AuthorDTO form) {
        mapService.updateAuthor(form);
        return "redirect:/allbook";
    }
}
