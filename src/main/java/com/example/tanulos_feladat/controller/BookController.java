package com.example.tanulos_feladat.controller;

import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.service.AuthorServiceImpl;
import com.example.tanulos_feladat.service.BookServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {

    private AuthorServiceImpl authorService;
    private BookServiceImpl bookService;

    public BookController(AuthorServiceImpl authorService, BookServiceImpl bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }


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

    @GetMapping(value = "/viewbook{id}")
    public String newBook(Model model,
                          @RequestParam("id") Long id) {
        BookDTO bookDTO = bookService.findBookById(id);
        model.addAttribute("book", bookDTO);

//        List<AuthorDTO> authorDTO = authorService.getAllAuthors();
        return "viewbook";
    }

}
