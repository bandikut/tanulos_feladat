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

//TODO service impl

@Transactional
@Service
public class AuthorService {
    //TODO ne legyen autowired
    //irm-ben a rendszerparaméterezőben, külön mapper rétegek
    //szétszedni a serviceket

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

    //mapstructort is lehet használni
    private AuthorDTO convertAuthorsDTO(Author author) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(author, AuthorDTO.class);
    }

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll()
                .stream().map(this::convertAuthorsDTO)
                .collect(Collectors.toList());
    }

    //TODO custom queryből a repóból
    private Integer authorsListSize() {
        return getAllAuthors().size();
    }

    public void addAuthor(AuthorDTO authorDTO) {
        var author = new ModelMapper().map(authorDTO, Author.class);
        authorRepository.save(author);
    }


    public void removeAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public AuthorDTO findAuthorById(Long id) {
        //getone lehibakezeli, ha nincs ilyen id
        return convertAuthorsDTO(authorRepository.getOne(id));
    }

    public List<AuthorDTO> findAuthorsByFirstname(String text) {
        return authorRepository.findByAuthorFirstNameContainingIgnoreCase(text)
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
        return new PageImpl<AuthorDTO>(tempList, PageRequest.of(index, pageSize), authorsListSize());
        //TODO pageable-t leszedni repository-ból
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
                .map(this::convertBooksDTO)
                .collect(Collectors.toList());
    }


    public void addBook(BookDTO bookDTO) {
        Book book = new ModelMapper().map(bookDTO, Book.class);
        //addbook adja vissza a long id-t saveAndFlush-t +nézni, tranzakciókezelés
        bookRepository.save(book);
    }


    public void updateAuthor(AuthorDTO authorDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Author author = modelMapper.map(authorDTO, Author.class);

        //TODO ismétlés ne legyen
        if (authorRepository.findById(authorDTO.getId()).isPresent()) {
            Author updateAuthor = authorRepository.findById(authorDTO.getId()).get();
            updateAuthor.setAuthorFirstName(author.getAuthorFirstName());
            updateAuthor.setAuthorLastName(author.getAuthorLastName());
            System.out.println("+++++ toupdate" + author.getBookList());
            updateAuthor.setBookList(author.getBookList());
            Author savedAuthor = authorRepository.saveAndFlush(updateAuthor);
//            if (authorRepository.findById(savedAuthor.getId()).isPresent()) {
//                System.out.println("sikeres mentés");
//            }
        }

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


    //todo ezt innen megszüntenti
    //todo átírni külön a serviceket implementationokre

    public void saveAuthor() {
        Author orkeny = new Author();
        orkeny.setId(114);
        orkeny.setAuthorFirstName("István");
        orkeny.setAuthorLastName("Örkény");
        Book b = new Book();
        b.setNumberOfPages(200);
        b.setIsbn("hsaihsiu");
        b.setAvailable(true);
        b.setTitle("macska");
        b.setId(1l);
        bookRepository.save(b);
        orkeny.getBookList().add(b);
        System.out.println("b" + b);
        authorRepository.save(orkeny);
        System.out.println("orkeny" + orkeny);
    }
}
