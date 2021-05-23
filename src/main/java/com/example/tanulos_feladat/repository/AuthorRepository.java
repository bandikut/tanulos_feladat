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


    @Query(nativeQuery = true, value = "SELECT * FROM Author a WHERE LOWER(a.author_last_name) LIKE LOWER(CONCAT('%',:name, '%'))" +
            "OR LOWER(a.author_first_name) LIKE LOWER(CONCAT('%',:name, '%'))")
    List<Author> findAuthorInWholeName(@Param("name") String name);

    @Query(nativeQuery = true, value = "select *  from Author a where a.id > :index order by a.author_last_name asc fetch first :size rows only ")
    List<Author> cardPagination(@Param("index") Integer index, @Param("size") Integer size);

}
