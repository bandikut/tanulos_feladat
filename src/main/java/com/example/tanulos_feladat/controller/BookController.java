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
public class BookController {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;

    @GetMapping(value = "/addbook")
    public String newBook(Model model) {
        model.addAttribute("nbook", new BookDTO());
        return "addbook";
    }

    @PostMapping(value = "/saveBook")
    public String saveBook(@ModelAttribute BookDTO form) {
        bookService.addBook(form);
        return "redirect:/addbook";
    }

    @RequestMapping(value = "/viewbook{id}", method = RequestMethod.GET)
    public String newBook(Model model,
                          @RequestParam("id") Long id) {
        BookDTO bookDTO = bookService.findBookById(id);
        model.addAttribute("book", bookDTO);

        List<AuthorDTO> authorDTO = authorService.getAllAuthors();
        return "viewbook";
    }

}
