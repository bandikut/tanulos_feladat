package com.example.tanulos_feladat.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "fk_author_id")),
            inverseJoinColumns = @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "fk_book_id"))
    )
    private Set<Author> authorList = new HashSet<>();

    public Book(Long id,String title, String isbn, boolean isAvailable, int numberOfPages) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.isAvailable = isAvailable;
        this.numberOfPages = numberOfPages;
    }


//    public void addAuthor(Author author) {
//        this.authors.add(author);
//    }
//
//    public void removeAuthor(Author author) {
//        this.authors.remove(author);
//    }
}
