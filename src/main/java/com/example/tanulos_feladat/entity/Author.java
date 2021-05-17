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
    private long id;

    @Column(nullable = false)
    private String authorFirstName;

    @Column(nullable = false)
    private String authorLastName;

    @ManyToMany(mappedBy = "authorList")
    private List<Book> bookList = new ArrayList<>();
}
