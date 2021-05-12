package com.example.tanulos_feladat.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<BookDTO> bookList = new HashSet<>();

}
