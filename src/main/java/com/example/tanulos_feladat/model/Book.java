package com.example.tanulos_feladat.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
//@AllArgsConstructor
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
            name = "author_first_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String authorFisrstName;

    @Column(
            name = "author_last_name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String authorLastName;

    @Column(
            name = "title",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String title;

    @Column(
            name = "isbn",
            nullable = true,
            columnDefinition = "TEXT"
    )
    private String isbn;

    @Column(
            name = "status",
            updatable = true,
            nullable = true
    )
    private boolean status;

    @Column(
            name = "number_of_pages",
            nullable = true
    )
    private int numberOfPages;

    public Book(String authorFisrstName, String authorLastName, String title, String isbn, boolean status, int numberOfPages) {
        this.authorFisrstName = authorFisrstName;
        this.authorLastName = authorLastName;
        this.title = title;
        this.isbn = isbn;
        this.status = status;
        this.numberOfPages = numberOfPages;
    }
}
