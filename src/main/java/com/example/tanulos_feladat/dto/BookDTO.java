package com.example.tanulos_feladat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {
    private Long id;
    private String title;
    private String isbn;
    private Boolean isAvailable;
    private Integer numberOfPages;
    private List<AuthorDTO> authorDTOList = new ArrayList<>();

    public BookDTO(Long id, String title, String isbn, Boolean isAvailable, Integer numberOfPages) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.isAvailable = isAvailable;
        this.numberOfPages = numberOfPages;
    }
}
