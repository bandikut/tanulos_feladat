package com.example.tanulos_feladat.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity(name = "Author")
@Table(name = "author")
public class Author {

    @Id
    @SequenceGenerator(
            name = "author_seq",
            sequenceName = "au_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "au_seq")
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "author_first_name", nullable = false, columnDefinition = "VARCHAR(150)")
    private String authorFirstName;

    @Column(name = "author_last_name", nullable = false, columnDefinition = "VARCHAR(150)")
    private String authorLastName;

    @ManyToMany(mappedBy = "authorList", fetch = FetchType.EAGER, targetEntity = Book.class, cascade = CascadeType.MERGE)
    private Set<Book> booksList = new HashSet<>();


}
