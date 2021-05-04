package com.example.tanulos_feladat.model;

import lombok.*;
import org.hibernate.annotations.Generated;

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
    @Column(
            name = "id",
            updatable = false
    )
    private long id;

    @Column(
            name = "author_first_name",
            nullable = false,
            columnDefinition = "VARCHAR(150)"
    )
    private String authorFirstName;

    @Column(
            name = "author_last_name",
            nullable = false,
            columnDefinition = "VARCHAR(150)"
    )
    private String authorLastName;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<Book>();


    public Author(String authorFirstName, String authorLastName) {
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
    }

}
