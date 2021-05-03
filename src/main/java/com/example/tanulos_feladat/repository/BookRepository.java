package com.example.tanulos_feladat.repository;

import com.example.tanulos_feladat.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {


}
