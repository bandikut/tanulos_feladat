package com.example.tanulos_feladat.controller;

import com.example.tanulos_feladat.dto.AuthorDTO;
import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.service.AuthorService;
import com.example.tanulos_feladat.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthorController {


    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/addauthor")
    public String newAuthor(Model model) {
        model.addAttribute("nauthor", new AuthorDTO());
        return "addauthor";
    }

    @PostMapping(value = "/saveAuthor")
    public String saveAuthor(@ModelAttribute AuthorDTO form)  {
        authorService.addAuthor(form);
        return "redirect:/addauthor";
    }

    @GetMapping(value = "/viewauthor")
    public String authorsBooks(Model model,
                               @RequestParam("id") Long id) {
        AuthorDTO authorDTO = authorService.findAuthorById(id);
        model.addAttribute("author", authorDTO);
        List<BookDTO> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "viewauthor";
    }

    @GetMapping(value = "/deleteauthor")
    public String deleteAuthor(@RequestParam("id") Long id) {
        authorService.deleteAuthor(id);
        System.out.println("id :" + id);
        return "redirect:/allbook";
    }


    @PostMapping(value = "/viewauthor/update{id}")
    public String updateAuthor(@ModelAttribute AuthorDTO form) {
        authorService.updateAuthor(form);
        return "redirect:/allbook";
    }
}
