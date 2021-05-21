package com.example.tanulos_feladat.service;

import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.entity.Author;
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
    public BookDTO convertBooksToDTO(Book book) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        BookDTO bookDto = modelMapper.map(book, BookDTO.class);
        return bookDto;
    }

    @Override
    public Book convertBooksDTOToBook(BookDTO bookDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Book book = modelMapper.map(bookDTO, Book.class);
        return book;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::convertBooksToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public void addBook(BookDTO bookDTO) {
        boolean exist = bookRepository.existsBooksByIsbn(bookDTO.getIsbn());

        if (!exist || bookDTO.getIsbn().isEmpty()) {
            Book book = convertBooksDTOToBook(bookDTO);
            bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public void updateBook(BookDTO bookDTO) {
        boolean exist = bookRepository.existsById(bookDTO.getId());

        if (exist) {
            Book updateBook = bookRepository.getOne(bookDTO.getId());
            updateBook.setTitle(bookDTO.getTitle());
            updateBook.setIsAvailable(bookDTO.getIsAvailable());
            updateBook.setIsbn(bookDTO.getIsbn());
            updateBook.setNumberOfPages(bookDTO.getNumberOfPages());
            updateBook.getAuthorList()
                    .addAll(bookDTO.getAuthorDTOList().stream()
                            .map(i -> modelMapper.map(i, Author.class))
                            .collect(Collectors.toList()));
            bookRepository.saveAndFlush(updateBook);
        }

    }

    @Override
    public BookDTO findBookById(Long id) {
        Book book =bookRepository.getOne(id);
        return convertBooksToDTO(book);
    }

    @Override
    public Integer numberOfBooks() {
        return bookRepository.booksNumber();
    }

}
