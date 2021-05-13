package com.example.tanulos_feladat.controller;

import com.example.tanulos_feladat.dto.AuthorDTO;
import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

        authors.stream().forEach(i -> {
            StringBuilder sb = new StringBuilder();
            sb.append(i.getFirstName() + " ").append(i.getLastName());
            i.getBookList().stream().forEach(b -> {
                sb.append("  " + b.getTitle() + " ");
            });
//            System.out.println(sb.toString());
        });


//        AuthorDTO joNesbo =mapService.findAuthorById(7L);
//        Set<BookDTO> josBook = joNesbo.getBookList();
//        BookDTO bosz =mapService.findBookById(600L);
//        BookDTO istenek =mapService.findBookById(3L);
//        josBook.add(bosz);
//        josBook.add(istenek);
//        System.out.println("josBook" + josBook);;
//        joNesbo.setBookList(josBook);
//        System.out.println("josbook "+ josbook);
//        mapService.updateAuthor(joNesbo);
//        System.out.println("jo" + jo);
//        System.out.println("authors" + authors);


        mapService.saveAuthor();

        int currentPage = pageIndex.orElse(0);
        Page<AuthorDTO> authorPagination = mapService.pagination(currentPage);
        model.addAttribute("authorPagination", authorPagination);
        int totalPages = authorPagination.getTotalPages();
        List<Integer> pageNumbers = new ArrayList<>();
        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
        } else {
            pageNumbers.add(0);
        }
        model.addAttribute("pageNumbers", pageNumbers);

        List<BookDTO> books = mapService.getAllBooks();
        model.addAttribute("books", books);

        return "index";
    }

}
