package com.example.tanulos_feladat.service;

import com.example.tanulos_feladat.dto.AuthorDTO;
import com.example.tanulos_feladat.entity.Author;
import com.example.tanulos_feladat.entity.Book;
import com.example.tanulos_feladat.repository.AuthorRepository;
import com.example.tanulos_feladat.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;
    BookRepository bookRepository;
    ModelMapper modelMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public AuthorDTO convertAuthorsDTO(Author author) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        AuthorDTO authorDto = modelMapper.map(author, AuthorDTO.class);
        return authorDto;
    }

    @Override
    public Author convertDTOtoAuthor(AuthorDTO authorDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Author author = modelMapper.map(authorDTO, Author.class);
        return author;
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll()
                .stream().map(this::convertAuthorsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Author> getAllAuthorsName() {
        return authorRepository.getAllAuthorsName();
    }

    @Override
    public Integer numberOfAuthors() {
        return authorRepository.authorsNumber();
    }

    @Override
    public void addAuthor(AuthorDTO authorDTO) {
        Author author = new ModelMapper().map(authorDTO, Author.class);
        authorRepository.save(author);
    }

    @Override
    public void removeAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorDTO findAuthorById(Long id) {
        //getone lehibakezeli, ha nincs ilyen id
        return convertAuthorsDTO(authorRepository.getOne(id));
    }

    @Override
    public List<AuthorDTO> findAuthorsByFirstname(String text) {
        return authorRepository.findByAuthorFirstNameContainingIgnoreCase(text)
                .stream().map(this::convertAuthorsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<AuthorDTO> pagination(int index) {
        int pageSize = 8;
        int startItemIndex = index * pageSize;
        List<AuthorDTO> tempList;

        if (numberOfAuthors() < startItemIndex) {
            tempList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItemIndex + pageSize, numberOfAuthors());
            tempList = getAllAuthors().subList(startItemIndex, toIndex);
            tempList.sort(Comparator.comparing(AuthorDTO::getFirstName));
        }
        return new PageImpl<>(tempList, PageRequest.of(index, pageSize), numberOfAuthors());
        //TODO pageable-t leszedni repository-ból
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
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


    //todo ezt innen megszüntenti
    //todo átírni külön a serviceket implementationokre

    public void saveAuthor() {
        Author orkeny = new Author();
        orkeny.setId(114L);
        orkeny.setAuthorFirstName("István");
        orkeny.setAuthorLastName("Örkény");
        Book b = new Book();
        b.setNumberOfPages(200);
        b.setIsbn("hsaihsiu");
        b.setIsAvailable(true);
        b.setTitle("macska");
        b.setId(1l);
        bookRepository.save(b);
        orkeny.getBookList().add(b);
        System.out.println("b" + b);
        authorRepository.save(orkeny);
        System.out.println("orkeny" + orkeny);
    }
}
