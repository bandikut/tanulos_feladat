package com.example.tanulos_feladat.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString

@Entity(name = "Book")
@Table(
        name = "book",
        uniqueConstraints = {
                @UniqueConstraint(name = "book_isbn_unique", columnNames = "isbn")
        }
)
public class Book {
    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence")

    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "title",
            nullable = false,
            columnDefinition = "VARCHAR(150)"
    )
    private String title;

    @Column(
            name = "isbn",
            nullable = true,
            columnDefinition = "VARCHAR(13)"
    )
    private String isbn;

    @Column(
            name = "is_available",
            updatable = true,
            nullable = true
    )
    private boolean isAvailable;

    @Column(
            name = "number_of_pages",
            nullable = true
    )
    private int numberOfPages;


    @Column(
            name = "moly_book_id",
            nullable = true
    )
    private int molyBookID;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(
                    name = "book_id",
                    foreignKey = @ForeignKey(name = "fk_author_id")),
            inverseJoinColumns = @JoinColumn(
                    name = "author_id",
                    foreignKey = @ForeignKey(name = "fk_book_id"))
    )
    private Set<Author> authors = new HashSet<Author>();

    public Book(String title, String isbn, boolean isAvailable, int numberOfPages) {
        this.title = title;
        this.isbn = isbn;
        this.isAvailable = isAvailable;
        this.numberOfPages = numberOfPages;
    }
}
