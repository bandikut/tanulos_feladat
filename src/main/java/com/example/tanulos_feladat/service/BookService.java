package com.example.tanulos_feladat.service;

import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.entity.Book;

import java.util.List;

public interface BookService {

    BookDTO convertBooksDTO(Book book);

    List<BookDTO> getAllBooks();

    void addBook(BookDTO bookDTO);

    BookDTO findBookById(Long id);
}
