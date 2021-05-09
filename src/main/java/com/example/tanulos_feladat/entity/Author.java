package com.example.tanulos_feladat.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString

@Entity(name = "Author")
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "author_first_name", nullable = false, columnDefinition = "VARCHAR(150)")
    private String authorFirstName;

    @Column(name = "author_last_name", nullable = false, columnDefinition = "VARCHAR(150)")
    private String authorLastName;

    @ManyToMany(mappedBy = "authorList", fetch = FetchType.LAZY)
    private Set<Book> booksList = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "fk_book_author"))
    private Book book;


    public Author(String authorFirstName, String authorLastName) {
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
    }


//    public void addBook(Book book) {
//        this.books.add(book);
//    }
//
//    public void removeBook(Book book) {
//        this.books.remove(book);
//    }
}
