package com.example.tanulos_feladat.service;

import com.example.tanulos_feladat.dto.AuthorDTO;
import com.example.tanulos_feladat.dto.BookDTO;

import com.example.tanulos_feladat.entity.Author;
import com.example.tanulos_feladat.entity.Book;

import com.example.tanulos_feladat.repository.AuthorRepository;
import com.example.tanulos_feladat.repository.BookRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MapService {
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ModelMapper modelMapper;

    //mapped by manual method
    private AuthorDTO convertAuthorsBooksDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setFirstName(author.getAuthorFirstName());
        authorDTO.setLastName(author.getAuthorLastName());
        Book book = author.getBook();
        authorDTO.setTitle(book.getTitle());
        return authorDTO;
    }


    public List<AuthorDTO> getAllAuthors() {
        return ((List<Author>) authorRepository.findAll())
                .stream().map(this::convertAuthorsBooksDTO)
                .collect(Collectors.toList());
    }


    //mapped by modelmapper method
    private BookDTO convertBooksDTO(Book book) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        BookDTO bookDTO = modelMapper
                .map(book, BookDTO.class);
        return bookDTO;
    }

    public List<BookDTO> getAllBooks() {
        return ((List<Book>) bookRepository.findAll())
                .stream()
                .map(obj -> modelMapper.map(obj, BookDTO.class))
                .collect(Collectors.toList());
    }


}
