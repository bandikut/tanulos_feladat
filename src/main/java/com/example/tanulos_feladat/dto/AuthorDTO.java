package com.example.tanulos_feladat.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    //TODO: Listté kell alakítani
    private Set<BookDTO> bookList = new HashSet<>();
}
