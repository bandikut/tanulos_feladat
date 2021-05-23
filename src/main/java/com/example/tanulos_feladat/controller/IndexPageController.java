package com.example.tanulos_feladat.controller;

import com.example.tanulos_feladat.dto.AuthorDTO;
import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.service.AuthorServiceImpl;
import com.example.tanulos_feladat.service.BookServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class IndexPageController {

    private AuthorServiceImpl authorService;
    private BookServiceImpl bookService;

    public IndexPageController(AuthorServiceImpl authorService, BookServiceImpl bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping(value = {"/", "/allbook**"})
    public String indexPage(
            Model model,
            @RequestParam("page") Optional<Integer> pageIndex) {
        List<AuthorDTO> authors = authorService.getAllAuthors();

        /** Authorservice gets the items from a certain index adding to the size of a page */
        int currentPage = pageIndex.orElse(0);
        int itemsInAPage = 8;
        List<AuthorDTO> authorPagination = authorService.pagination(currentPage, itemsInAPage);
        int totalPages = authorService.numberOfAuthors() / itemsInAPage + 1;
        List<Integer> pageNumbers = new ArrayList<>(Collections.singletonList(0));

        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
        }

        List<BookDTO> books = bookService.getAllBooks();

        model.addAttribute("authors", authors);
        model.addAttribute("authorPagination", authorPagination);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("books", books);

        return "index";
    }

}
