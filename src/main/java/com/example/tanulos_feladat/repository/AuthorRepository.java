package com.example.tanulos_feladat.repository;

import com.example.tanulos_feladat.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByAuthorFirstNameContainingIgnoreCase(String text1);

    @Query("select authorFirstName, authorLastName from Author")
    List<Author> getAllAuthorsName();

    @Query("select count (id) from Author ")
    Integer authorsNumber();
}
