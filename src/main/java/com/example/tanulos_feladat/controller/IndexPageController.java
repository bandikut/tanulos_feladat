package com.example.tanulos_feladat.controller;

import com.example.tanulos_feladat.dto.AuthorDTO;
import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.service.AuthorServiceImpl;
import com.example.tanulos_feladat.service.BookServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        model.addAttribute("authors", authors);

        authors.stream().forEach(i -> {
            StringBuilder sb = new StringBuilder();
            sb.append(i.getFirstName() + " ").append(i.getLastName());
            i.getBookList().stream().forEach(b -> sb.append("  " + b.getTitle() + " "));
        });


        int currentPage = pageIndex.orElse(0);
        Page<AuthorDTO> authorPagination = authorService.pagination(currentPage);
        model.addAttribute("authorPagination", authorPagination);
        int totalPages = authorPagination.getTotalPages();
        List<Integer> pageNumbers = new ArrayList<>();
        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
        } else {
            pageNumbers.add(0);
        }
        model.addAttribute("pageNumbers", pageNumbers);

        List<BookDTO> books = bookService.getAllBooks();
        model.addAttribute("books", books);

        return "index";
    }

}
