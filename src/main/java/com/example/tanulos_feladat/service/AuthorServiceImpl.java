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
    public void addAuthor(AuthorDTO authorDTO) {
        boolean exist = authorRepository.existsAuthorByAuthorFirstNameIgnoreCaseAndAuthorLastNameIgnoreCase(authorDTO.getFirstName(), authorDTO.getLastName());
        if (!exist) {
            authorRepository.saveAndFlush(convertDTOtoAuthor(authorDTO));
        }
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public void updateAuthor(AuthorDTO authorDTO) {
        boolean exist = authorRepository.existsById(authorDTO.getId());

        if (exist) {
            Author updateAuthor = authorRepository.getOne(authorDTO.getId());
            updateAuthor.setAuthorFirstName(authorDTO.getFirstName());
            updateAuthor.setAuthorLastName(authorDTO.getLastName());
            updateAuthor.getBookList()
                    .addAll(authorDTO.getBookList().stream()
                            .map(i -> modelMapper.map(i, Book.class))
                            .collect(Collectors.toList()));
            authorRepository.saveAndFlush(updateAuthor);
        }

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
    public AuthorDTO findAuthorById(Long id) {
        Author author = authorRepository.getOne(id);
        return convertAuthorsDTO(author);
    }

    @Override
    public List<AuthorDTO> findAuthorsInWholeName(String name) {
        return  authorRepository.findAuthorInWholeName(name)
                .stream().map(this::convertAuthorsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuthorDTO> findAuthorsByFirstname(String firstName) {
        return authorRepository.findByAuthorFirstNameContainingIgnoreCase(firstName)
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
    }

}
