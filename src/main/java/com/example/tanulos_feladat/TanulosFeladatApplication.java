package com.example.tanulos_feladat;

import com.example.tanulos_feladat.model.Book;
import com.example.tanulos_feladat.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TanulosFeladatApplication {

    public static void main(String[] args) {
        SpringApplication.run(TanulosFeladatApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository) {
        return args -> {
            Book hyperion = new Book(
                    "Dan", "Simmons", "Hyperion 1", "NIJO1616161", true, 670
            );
            bookRepository.save(hyperion);
			System.out.println(bookRepository.findAll());
        };
    }

}
