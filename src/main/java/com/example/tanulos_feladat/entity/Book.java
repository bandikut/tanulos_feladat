package com.example.tanulos_feladat.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "book_isbn_unique", columnNames = "isbn")}
)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String isbn;

    private Boolean isAvailable;

    private int numberOfPages;

    @ManyToMany
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"
                    ,foreignKey = @ForeignKey(name = "fk_author_id")
            ),
            inverseJoinColumns = @JoinColumn(name = "author_id"
                    ,foreignKey = @ForeignKey(name = "fk_book_id")
            )
    )
    private List<Author> authorList = new ArrayList<>();


    public Book(Long id, String title, String isbn, Boolean isAvailable, int numberOfPages) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.isAvailable = isAvailable;
        this.numberOfPages = numberOfPages;
    }
}
