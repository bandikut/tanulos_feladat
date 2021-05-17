package com.example.tanulos_feladat.service;

import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.entity.Book;
import com.example.tanulos_feladat.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    BookRepository bookRepository;
    ModelMapper modelMapper;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDTO convertBooksDTO(Book book) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        var bookDto = modelMapper.map(book, BookDTO.class);
        return bookDto;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return ((List<Book>) bookRepository.findAll())
                .stream()
                .map(this::convertBooksDTO)
                .collect(Collectors.toList());
    }


    @Override
    public void addBook(BookDTO bookDTO) {
        var book = new ModelMapper().map(bookDTO, Book.class);
        //addbook adja vissza a long id-t saveAndFlush-t +nézni, tranzakciókezelés
        bookRepository.save(book);
    }

    @Override
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