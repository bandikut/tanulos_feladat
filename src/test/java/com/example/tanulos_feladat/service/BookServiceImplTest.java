package com.example.tanulos_feladat.service;

import com.example.tanulos_feladat.dto.AuthorDTO;
import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.entity.Author;
import com.example.tanulos_feladat.entity.Book;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceImplTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    void whenConvertBookToBookDTOCorrect() {
        Book egypercesek = new Book();
        egypercesek.setId(120L);
        egypercesek.setTitle("Egypercesek");
        egypercesek.setIsbn("9630945231");
        egypercesek.setNumberOfPages(200);
        egypercesek.setIsAvailable(true);

        Author stephenK = new Author();
        stephenK.setId(114L);
        stephenK.setAuthorLastName("Stephen");
        stephenK.setAuthorLastName("King");

        egypercesek.getAuthorList().add(stephenK);

        BookDTO bookDTO = modelMapper.map(egypercesek, BookDTO.class);
        assertEquals(egypercesek.getId(), bookDTO.getId()); //todo paraméterként beletenni, h minek kellene lennie
        assertEquals(egypercesek.getTitle(), bookDTO.getTitle());
        assertEquals(egypercesek.getIsbn(), bookDTO.getIsbn());
        assertEquals(egypercesek.getNumberOfPages(), bookDTO.getNumberOfPages());
        assertEquals(egypercesek.getIsAvailable(), bookDTO.getIsAvailable());
//        assertEquals(egypercesek.getAuthorList(), bookDTO.getAuthorDTOList()); //book!=bookDTO, author!=authorDTO
    }

    @Test
    void whenConvertBookDTOToBookCorrect() {
        BookDTO azIntezet = new BookDTO();
        azIntezet.setId(120L);
        azIntezet.setTitle("Az intézet");
        azIntezet.setIsbn("9789635042524");
        azIntezet.setNumberOfPages(490);
        azIntezet.setIsAvailable(true);

        AuthorDTO stephenK = new AuthorDTO();
        stephenK.setId(114L);
        stephenK.setFirstName("Stephen");
        stephenK.setLastName("King");
        azIntezet.getAuthorDTOList().add(stephenK);

        Book book = modelMapper.map(azIntezet, Book.class);
        assertEquals(azIntezet.getId(), book.getId());
        assertEquals(azIntezet.getTitle(), book.getTitle());
        assertEquals(azIntezet.getIsbn(), book.getIsbn());
        assertEquals(azIntezet.getNumberOfPages(), book.getNumberOfPages());
        assertEquals(azIntezet.getIsAvailable(), book.getIsAvailable());
//        assertEquals(azIntezet.getAuthorDTOList(), book.getAuthorList()); //book!=bookDTO, author!=authorDTO
    }
}