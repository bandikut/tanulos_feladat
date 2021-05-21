package com.example.tanulos_feladat.service;

import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.entity.Book;

import java.util.List;

public interface BookService {

    BookDTO convertBooksToDTO(Book book);

    Book convertBooksDTOToBook(BookDTO bookDTO);

    void addBook(BookDTO bookDTO);

    void updateBook(BookDTO bookDTO);

    List<BookDTO> getAllBooks();

    BookDTO findBookById(Long id);

    Integer numberOfBooks();
}
