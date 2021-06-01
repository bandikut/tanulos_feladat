package com.example.tanulos_feladat.service;

import com.example.tanulos_feladat.dto.AuthorDTO;
import com.example.tanulos_feladat.entity.Author;

import java.util.List;

public interface AuthorService {

//

    List<AuthorDTO> getAllAuthors();

    void addAuthor(AuthorDTO authorDTO);

    AuthorDTO findAuthorById(Long id);

    List<AuthorDTO> findAuthorsInWholeName(String firstName);

    List<AuthorDTO> findAuthorsByFirstname(String text);

    List<AuthorDTO> pagination(Integer startIndex, Integer size);

    void deleteAuthor(Long id);

    void updateAuthor(AuthorDTO authorDTO);

    List<Author> getAllAuthorsName();

    Integer numberOfAuthors();
}
