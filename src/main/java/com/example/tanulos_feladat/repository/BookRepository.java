package com.example.tanulos_feladat.repository;

import com.example.tanulos_feladat.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsById(Long id);

    @Query("select count (id) from Book ")
    Integer booksNumber();

    boolean existsBooksByIsbn(String string);

}
