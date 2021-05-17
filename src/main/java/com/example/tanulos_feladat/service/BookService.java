package com.example.tanulos_feladat.service;

import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.entity.Book;
import com.example.tanulos_feladat.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ModelMapper modelMapper;

    //mapped by modelmapper method
    private BookDTO convertBooksDTO(Book book) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(book, BookDTO.class);
    }

    public List<BookDTO> getAllBooks() {
        return ((List<Book>) bookRepository.findAll())
                .stream()
                .map(this::convertBooksDTO)
                .collect(Collectors.toList());
    }


    public void addBook(BookDTO bookDTO) {
        var book = new ModelMapper().map(bookDTO, Book.class);
        //addbook adja vissza a long id-t saveAndFlush-t +nézni, tranzakciókezelés
        bookRepository.save(book);
    }
    public BookDTO findBookById(Long id) {
        BookDTO book;
        //todo megoldani
        if (bookRepository.findById(id).isPresent()) {
            book = convertBooksDTO(bookRepository.findById(id).get());
        } else {
            book = null;
        }
        return book;
    }
}
