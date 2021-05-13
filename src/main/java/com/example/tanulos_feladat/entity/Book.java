package com.example.tanulos_feladat.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity(name = "Book")
@Table(name = "book",
        uniqueConstraints = {
                @UniqueConstraint(name = "book_isbn_unique", columnNames = "isbn")}
)
public class Book {
    @Id
    @SequenceGenerator(
            name = "book_seq",
            sequenceName = "bo_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bo_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "is_available")
    private boolean isAvailable;

    @Column(name = "number_of_pages")
    private int numberOfPages;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Author.class,
            cascade = CascadeType.MERGE)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id",
                    foreignKey = @ForeignKey(name = "fk_author_id")),
            inverseJoinColumns = @JoinColumn(name = "author_id",
                    foreignKey = @ForeignKey(name = "fk_book_id"))
    )
    private Set<Author> authorList = new HashSet<>();

}
