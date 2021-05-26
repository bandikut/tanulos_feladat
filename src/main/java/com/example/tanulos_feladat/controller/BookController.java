package com.example.tanulos_feladat.controller;

import com.example.tanulos_feladat.client.BookClient;
import com.example.tanulos_feladat.dto.AuthorDTO;
import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.service.AuthorServiceImpl;
import com.example.tanulos_feladat.service.BookServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
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
        log.debug("authorname " + authorname);
        searchAuthors = authorService.findAuthorsInWholeName(authorname);
        log.debug("searchAuthors " + searchAuthors);
        return redirectToAddbook;
    }

    @GetMapping(value = "/addbook/addauthor")
    public String addAuthorToList(String authId) {
        authorsOfBook.addAll(searchAuthors.stream().filter(i -> i.getId() == Long.parseLong(authId)).collect(Collectors.toList()));
        searchAuthors.removeAll(authorsOfBook);
        return redirectToAddbook;
    }

    @GetMapping(value = "/addbook/removeauthor")
    public String removeAuthorFromList(String authId) {
        searchAuthors.addAll(authorsOfBook.stream().filter(i -> i.getId() == Long.parseLong(authId)).collect(Collectors.toList()));
        authorsOfBook.removeAll(searchAuthors);
        return redirectToAddbook;
    }

    @GetMapping(value = "/addbook/reset")
    public String resetForm() {
        authorsOfBook.clear();
        return redirectToAddbook;
    }

//    @PostMapping(value = "/savebook")
//    public String savebook(@ModelAttribute BookDTO bookForm) {
//        bookForm.getAuthorDTOList().addAll(authorsOfBook);
//        bookService.addBook(bookForm);
//        authorsOfBook.clear();
//        searchAuthors.clear();
//        return redirectToAddbook;
//    }

    @PostMapping(value = "/savebook")
    public RedirectView saveBook2(
            HttpServletRequest request,
            @ModelAttribute BookDTO bookDTO,
            RedirectAttributes redirectAttributes,
            BindingResult bindingResult) {
        if (BookDTO.isValid(bookDTO)) {
            redirectAttributes.addFlashAttribute("bookDTO", bookDTO);
            return new RedirectView("/savebook/booksavesucess", true);
        } else {
            log.debug("not valid");
            return  new RedirectView("/addbook", true);
        }

    }

    @GetMapping(value = "/savebook/booksavesucess")
    public String saveSuccess(
            HttpServletRequest httpServletRequest) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
        if (inputFlashMap != null) {
            BookDTO newBookDTO =  (BookDTO) inputFlashMap.get("bookDTO");
            log.debug("newBookDTO", newBookDTO);
            return "booksavesucess";
        } else {
            return "redirect:/savebook";
        }
    }

    @GetMapping(value = "/viewbook{id}")
    public String newBook(Model model,
                          @RequestParam("id") Long id) {
        BookDTO bookDTO = bookService.findBookById(id);
        model.addAttribute("book", bookDTO);
        return "viewbook";
    }

}
