package com.example.tanulos_feladat;

import com.example.tanulos_feladat.repository.AuthorRepository;
import com.example.tanulos_feladat.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TanulosFeladatApplication {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(TanulosFeladatApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository, AuthorRepository authorRepository) {
        return args -> {
//            Book hyperion = new Book(
//                     "Hyperion", "9789634195764", true, 538
//            );
//            Book terror = new Book(
//                     "Terror", "9789634194347", true, 714
//            );
//            Book istenek = new Book(
//                     "Istenek és emberek", "9789634335061", true, 240
//            );
//            bookRepository.save(hyperion);
//            bookRepository.save(terror);
//            bookRepository.save(istenek);
//			System.out.println(bookRepository.findAll());
//            Author dans = new Author("Dan", "Simmons");
//            Author jonesb = new Author("Jo", "Nesbo");
//            authorRepository.save(dans);
//            authorRepository.save(jonesb);


        };
    }

}
