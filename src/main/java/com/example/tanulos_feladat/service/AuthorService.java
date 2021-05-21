package com.example.tanulos_feladat.service;

import com.example.tanulos_feladat.dto.AuthorDTO;
import com.example.tanulos_feladat.entity.Author;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuthorService {

    AuthorDTO convertAuthorsDTO(Author author);

    Author convertDTOtoAuthor(AuthorDTO authorDTO);

    List<AuthorDTO> getAllAuthors();

    void addAuthor(AuthorDTO authorDTO);

    AuthorDTO findAuthorById(Long id);

    List<AuthorDTO> findAuthorsInWholeName(String firstName);

    List<AuthorDTO> findAuthorsByFirstname(String text);

    Page<AuthorDTO> pagination(int index);

    void deleteAuthor(Long id);

    void updateAuthor(AuthorDTO authorDTO);

    List<Author> getAllAuthorsName();

    Integer numberOfAuthors();
}
