package com.example.tanulos_feladat.controller;

import com.example.tanulos_feladat.dto.AuthorDTO;
import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
public class BookController {

    @Autowired
    private MapService mapService;

    @GetMapping(value = "/addbook")
    public String newBook(Model model) {
        model.addAttribute("nbook", new BookDTO());
        return "addbook";
    }

    @PostMapping(value = "/saveBook")
    public String saveBook(@ModelAttribute BookDTO form) {
        mapService.addBook(form);
        return "redirect:/addbook";
    }

    @RequestMapping(value = "/viewbook{id}", method = RequestMethod.GET)
    public String newBook(Model model,
                          @RequestParam("id") Long id) {
        BookDTO bookDTO = mapService.findBookById(id);
        model.addAttribute("book", bookDTO);

        List<AuthorDTO> authorDTO = mapService.getAllAuthors();
        return "viewbook";
    }

}
