package com.example.tanulos_feladat.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class AuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private List<BookDTO> bookList = new ArrayList<>();

    public AuthorDTO(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
