package com.example.tanulos_feladat.service;

import com.example.tanulos_feladat.dto.AuthorDTO;
import com.example.tanulos_feladat.dto.BookDTO;
import com.example.tanulos_feladat.entity.Author;
import com.example.tanulos_feladat.entity.Book;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.DestinationSetter;

import javax.print.attribute.standard.Destination;

import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceImplTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    void whenConvertAuthorDTOToAuthorCorrect() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        Author orkeny = new Author();
        orkeny.setId(114L);
        orkeny.setAuthorFirstName("István");
        orkeny.setAuthorLastName("Örkény");

        Book egypercesek = new Book();
        egypercesek.setNumberOfPages(200);
        egypercesek.setIsbn("9630945231");
        egypercesek.setIsAvailable(true);
        egypercesek.setTitle("Egypercesek");
        egypercesek.setId(120L);
        orkeny.getBookList().add(egypercesek);

        AuthorDTO authorDTO = modelMapper.map(orkeny, AuthorDTO.class);
        assertEquals(orkeny.getId(), authorDTO.getId());
        assertEquals(orkeny.getAuthorFirstName(), authorDTO.getFirstName());
        assertEquals(orkeny.getAuthorLastName(), authorDTO.getLastName());
//        assertEquals(orkeny.getBookList(), authorDTO.getBookList()); //book != bookdto
    }

    @Test
    void whenConvertAuthorToAuthorDTOCorrect() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        AuthorDTO stephenK = new AuthorDTO();
        stephenK.setId(114L);
        stephenK.setFirstName("Stephen");
        stephenK.setLastName("King");
        BookDTO b = new BookDTO();
        b.setNumberOfPages(490);
        b.setIsbn("9789635042524");
        b.setIsAvailable(true);
        b.setTitle("Az intézet");
        b.setId(120L);
        stephenK.getBookList().add(b);

        Author author = modelMapper.map(stephenK, Author.class);
        assertEquals(stephenK.getId(), author.getId());
        assertEquals(stephenK.getFirstName(), author.getAuthorFirstName());
        assertEquals(stephenK.getLastName(), author.getAuthorLastName());
//        assertEquals(stephenK.getBookList(), author.getBookList()); //book != bookdto
    }
}