package com.example.tanulos_feladat.dto;

import com.example.tanulos_feladat.entity.Book;
import lombok.Data;

@Data
public class AuthorDTO {
    private String firstName;
    private String lastName;
    private String title;
}
