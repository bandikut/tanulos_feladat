package com.example.tanulos_feladat.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private List<BookDTO> bookList = new ArrayList<>();
}
