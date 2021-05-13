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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MapService {
    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ModelMapper modelMapper;

//    //mapped by manual method
//    private AuthorDTO convertAuthorsBooksDTO(Author author) {
//        AuthorDTO authorDTO = new AuthorDTO();
//        authorDTO.setFirstName(author.getAuthorFirstName());
//        authorDTO.setLastName(author.getAuthorLastName());
//        Book book = author.getBook();
//        authorDTO.setTitle(book.getTitle());
//        return authorDTO;
//    }

    private AuthorDTO convertAuthorsDTO(Author author) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        AuthorDTO authorDTO = modelMapper
                .map(author, AuthorDTO.class);
        return authorDTO;
    }

    public List<AuthorDTO> getAllAuthors() {
        return ((List<Author>) authorRepository.findAll())
                .stream().map(this::convertAuthorsDTO)
                .collect(Collectors.toList());
    }

    private Integer authorsListSize() {
        return getAllAuthors().size();
    }

    @Transactional
    public void addAuthor(AuthorDTO authorDTO) {
        Author author = new ModelMapper().map(authorDTO, Author.class);
        authorRepository.save(author);
    }

    @Transactional
    public void removeAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public AuthorDTO findAuthorById(Long id) {
        AuthorDTO author;
        if (authorRepository.findById(id).isPresent()) {
            author = convertAuthorsDTO(authorRepository.findById(id).get());
        } else {
            author = null;
        }
        return author;
    }

    public List<AuthorDTO> findAuthorsByFirstname(String text) {
        return ((List<Author>) authorRepository.findByAuthorFirstNameContainingIgnoreCase(text))
                .stream().map(this::convertAuthorsDTO)
                .collect(Collectors.toList());
    }

    public Page<AuthorDTO> pagination(int index) {
        int pageSize = 8;
        int startItemIndex = index * pageSize;
        List<AuthorDTO> tempList;

        if (authorsListSize() < startItemIndex) {
            tempList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItemIndex + pageSize, authorsListSize());
            tempList = getAllAuthors().subList(startItemIndex, toIndex);
            tempList.sort(Comparator.comparing(AuthorDTO::getFirstName));
        }
        Page<AuthorDTO> onePage = new PageImpl<AuthorDTO>(tempList, PageRequest.of(index, pageSize), authorsListSize());
        return onePage;
    }

    @Transactional
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
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
                .map(this::convertBooksDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addBook(BookDTO bookDTO) {
        Book book = new ModelMapper().map(bookDTO, Book.class);
        bookRepository.save(book);
    }

    @Transactional
    public void updateAuthor(AuthorDTO authorDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Author author = modelMapper.map(authorDTO, Author.class);

        if (authorRepository.findById(authorDTO.getId()).isPresent()) {
            Author updateAuthor = authorRepository.findById(authorDTO.getId()).get();
            updateAuthor.setAuthorFirstName(author.getAuthorFirstName());
            updateAuthor.setAuthorLastName(author.getAuthorLastName());
            System.out.println("+++++ toupdate" + author.getBooksList());
            updateAuthor.setBooksList(author.getBooksList());
            Author savedAuthor = authorRepository.save(updateAuthor);
            if (authorRepository.findById(savedAuthor.getId()).isPresent()) {
                System.out.println("sikeres mentés");
            }
        }

    }

    public BookDTO findBookById(Long id) {
        BookDTO book;
        if (bookRepository.findById(id).isPresent()) {
            book = convertBooksDTO(bookRepository.findById(id).get());
        } else {
            book = null;
        }
        return book;
    }


    public void saveAuthor() {
        Author orkeny = new Author();
        orkeny.setId(113);
        orkeny.setAuthorFirstName("István");
        orkeny.setAuthorLastName("Örkény");
        Set<Book> bl = new HashSet<>();
        bl.add(bookRepository.findById(3L).get());
        orkeny.setBooksList(bl);
        authorRepository.save(orkeny);
    }
}
