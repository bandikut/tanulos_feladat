package com.example.tanulos_feladat.controller;

import com.example.tanulos_feladat.dto.AuthorDTO;
import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.service.AuthorServiceImpl;
import com.example.tanulos_feladat.service.BookServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private AuthorServiceImpl authorService;
    private BookServiceImpl bookService;

    public BookController(AuthorServiceImpl authorService, BookServiceImpl bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    private List<AuthorDTO> searchAuthors = new ArrayList<>();
    private List<AuthorDTO> authorsOfBook = new ArrayList<>();
    static final String redirectToAddbook = "redirect:/addbook";


    @GetMapping(value = "/addbook")
    public String newBook(Model model, HttpServletRequest request) {
        model.addAttribute("nbook", new BookDTO());
        model.addAttribute("nauthor", new AuthorDTO());
        model.addAttribute("authorList", searchAuthors);
        model.addAttribute("authorsOfBook", authorsOfBook);
        return "addbook";
    }

    @GetMapping(value = "/addbook/searchinauthors")
    public String searchInAuthors(String authorname) {
        searchAuthors.clear();
        searchAuthors = authorService.findAuthorsInWholeName(authorname);
        return redirectToAddbook;
    }

    @GetMapping(value = "/addbook/addauthor")
    public String addAuthorToList(Model model, String authId) {
        authorsOfBook.addAll(searchAuthors.stream().filter(i -> i.getId() == Long.parseLong(authId)).collect(Collectors.toList()));
        return redirectToAddbook;
    }

    @PostMapping(value = "/saveBook")
    public String saveBook(@ModelAttribute BookDTO bookForm)  {
        bookForm.getAuthorDTOList().addAll(authorsOfBook);
        bookService.addBook(bookForm);
        authorsOfBook.clear();
        searchAuthors.clear();
        return redirectToAddbook;
    }

    @GetMapping(value = "/viewbook{id}")
    public String newBook(Model model,
                          @RequestParam("id") Long id) {
        BookDTO bookDTO = bookService.findBookById(id);
        model.addAttribute("book", bookDTO);

        return "viewbook";
    }

}
