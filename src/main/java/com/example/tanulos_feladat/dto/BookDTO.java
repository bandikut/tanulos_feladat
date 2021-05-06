package com.example.tanulos_feladat.dto;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String isbn;
    private Boolean isAvailable;
    private Integer numberOfPages;
}
