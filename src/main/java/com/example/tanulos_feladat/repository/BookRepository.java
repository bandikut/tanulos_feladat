package com.example.tanulos_feladat.repository;

import com.example.tanulos_feladat.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsById(Long id);

    @Query("select count (id) from Book ")
    Integer booksNumber();

    boolean existsBooksByIsbn(String string);

}
