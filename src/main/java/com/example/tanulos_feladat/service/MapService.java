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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    public void removeAuthor(Long id) {
        authorRepository.deleteById(id);
    }

        public AuthorDTO findAuthorById(Long id) {
        AuthorDTO author;
        if (authorRepository.findById(id).isPresent()){
            author = convertAuthorsDTO(authorRepository.findById(id).get());
        } else {
            author = null;
        }
        return  author;
    }

  

    public List<AuthorDTO> findInWholeName(String text1, String tex2) {
        return ((List<Author>) authorRepository.findByAuthorFirstNameContainsOrAuthorLastNameContains(text1, tex2))
                .stream().map(this::convertAuthorsDTO)
                .collect(Collectors.toList());
    }

    public List<AuthorDTO> findAuthorsByFirstname(String text) {
        return ((List<Author>) authorRepository.findByAuthorFirstNameContainingIgnoreCase(text))
                .stream().map(this::convertAuthorsDTO)
                .collect(Collectors.toList());
    }

    public Page<AuthorDTO> pagination(int index) {
        int pageSize = 8;
        int startItemIndex = index*pageSize;
        List<AuthorDTO> tempList;

        if (authorsListSize() < startItemIndex) {
            tempList = Collections.emptyList();
        } else {
            int toIndex  = Math.min(startItemIndex + pageSize,authorsListSize());
            tempList = getAllAuthors().subList(startItemIndex, toIndex);
            tempList.sort(Comparator.comparing(AuthorDTO::getFirstName));
        }
        Page<AuthorDTO> onePage = new PageImpl<AuthorDTO>(tempList, PageRequest.of(index, pageSize), authorsListSize());
        return onePage;
    }

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
                .map(obj -> modelMapper.map(obj, BookDTO.class))
                .collect(Collectors.toList());
    }

    public void addBook(BookDTO bookDTO) {
        Book book = new ModelMapper().map(bookDTO, Book.class);
        bookRepository.save(book);
    }


    public void updateAuthor(AuthorDTO authorDTO) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Author author = modelMapper.map(authorDTO, Author.class);
        System.out.println("+-+-+-+-+-+author dto" + authorDTO);
        System.out.println("+-+-+-+-+-+author entity" + author);

        if ( authorRepository.findById(authorDTO.getId()).isPresent()){
           Author updateAuthor = authorRepository.findById(authorDTO.getId()).get();
            updateAuthor.setAuthorFirstName(author.getAuthorFirstName());
            updateAuthor.setAuthorLastName(author.getAuthorLastName());
            Author savedAuthor = authorRepository.save(updateAuthor);
            if (authorRepository.findById(savedAuthor.getId()).isPresent()){
                System.out.println("sikeres mentés");
            }
        }

    }
}
