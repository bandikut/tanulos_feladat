package com.example.tanulos_feladat.controller;

import com.example.tanulos_feladat.dto.AuthorDTO;
import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class IndexPageController {

    @Autowired
    private MapService mapService;

    @RequestMapping(value = {"/", "/allbook**"}, method = RequestMethod.GET)
    public String indexPage(
            Model model,
            @RequestParam("page") Optional<Integer> pageIndex) {
        List<AuthorDTO> authors = mapService.getAllAuthors();
        model.addAttribute("authors", authors);

        int currentPage = pageIndex.orElse(0);
        Page<AuthorDTO> authorPagination = mapService.pagination(currentPage);
        model.addAttribute("authorPagination", authorPagination);
        int totalPages = authorPagination.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
        }
        model.addAttribute("pageNumbers", totalPages);

        List<BookDTO> books = mapService.getAllBooks();
        model.addAttribute("books", books);

        return "index";
    }

    @GetMapping(value = "/addbook")
    public String newBook(Model model) {
        model.addAttribute("nbook", new BookDTO());
        return "addbook";
    }

    @PostMapping(value = "/saveBook")
    public String saveBook(@ModelAttribute BookDTO form, Model model) throws Exception {
        System.out.println("form  " + form.toString());
        mapService.addBook(form);
        return "redirect:/addbook";
    }

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

    @RequestMapping(value = "/authorstobooks{id}", method = RequestMethod.GET)
    public String authorsBooks(Model model,
                               @RequestParam("id") Long id) {
        AuthorDTO authorDTO = mapService.findAuthorById(id);
        model.addAttribute("author", authorDTO);
        return "authorstobooks";
    }

    @PostMapping(value = "/{deleteauthor}")
    public String deleteAuthor(@RequestParam("deleteauthor") Long id) {
        mapService.deleteAuthor(id);
        System.out.println("id :" + id);
        return "redirect:/allbook";
    }

    @RequestMapping(value = "/authorstobooks/update{id}", method = RequestMethod.POST)
    public String updateAuthor(@ModelAttribute AuthorDTO form) {
        System.out.println("///////////id  " + form.getId());
        System.out.println("///////////author  " + form);
        mapService.updateAuthor(form);
        return "redirect:/allbook";
    }


}
