package com.example.tanulos_feladat.repository;

import com.example.tanulos_feladat.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    boolean existsAuthorByAuthorFirstNameIgnoreCaseAndAuthorLastNameIgnoreCase(String firstName, String lastName);

    boolean existsById(Long id);

    @Query("select authorFirstName, authorLastName from Author")
    List<Author> getAllAuthorsName();

    @Query("select count (id) from Author ")
    Integer authorsNumber();

    List<Author> findByAuthorFirstNameContainingIgnoreCase(String text1);

    @Query("select a from Author a where lower(a.authorLastName) like %:name% or lower(a.authorFirstName)  like %:name%")
    List<Author> findAuthorInWholeName(@Param("name") String name);
}
