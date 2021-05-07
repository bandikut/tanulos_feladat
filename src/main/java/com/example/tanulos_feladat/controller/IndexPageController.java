package com.example.tanulos_feladat.controller;

import com.example.tanulos_feladat.dto.AuthorDTO;
import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.entity.Book;
import com.example.tanulos_feladat.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class IndexPageController {

    @Autowired
    private MapService mapService;

    @GetMapping(value = {"/", "/allbook"})
    public String indexPage(Model model) {
        Book b1 = new Book(35L, "teszt", "1234567891234", true, 310);
        Book c1 = new Book(45L, "teszt1", "8234567891234", true, 317);
        List<Book> bookList = new ArrayList<>(Arrays.asList(b1, c1));


        model.addAttribute("b1", b1);
        model.addAttribute("c1", c1);
        model.addAttribute("booklist", bookList);

        List<AuthorDTO> authors = mapService.getAllAuthors();
        model.addAttribute("authors", authors);

        List<BookDTO> books = mapService.getAllBooks();
        model.addAttribute("books", books);

        return "index";
    }

    @GetMapping(value = "/addbook")
    public String newBook(Model model) {
        model.addAttribute("nbook",new BookDTO());
        return "addbook";
    }

    @PostMapping(value = "/save")
    public String saveBook(@ModelAttribute BookDTO form, Model model) throws Exception {
        System.out.println("form  " + form.toString());
        mapService.addBook(form);
        return "redirect:/addbook";
    }

}
