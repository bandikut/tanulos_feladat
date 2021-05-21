package com.example.tanulos_feladat.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String authorFirstName;

    private String authorLastName;

    @ManyToMany(mappedBy = "authorList", cascade = CascadeType.ALL)
    private List<Book> bookList = new ArrayList<>();
}
