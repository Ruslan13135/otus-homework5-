package ot.homework5plus.rushm.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.domain.Genre;
import ot.homework5plus.rushm.domain.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DirtiesContext
    void addBook() {
        Mono<Book> bookMono = bookRepository.save(new Book("Test Book", new Author("Test Author"), new Genre("Test Genre"), null));
        StepVerifier
                .create(bookMono)
                .assertNext(book -> assertNotNull(book.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    void shouldReturnAllCorrectBooks() {
        Flux<Book> bookFlux = bookRepository.findAll();
        StepVerifier
                .create(bookFlux)
                .recordWith(ArrayList::new)
                .expectNextCount(5)
                .consumeRecordedWith(results -> {
                    assertThat(results)
                            .extracting(Book::getTitle)
                            .contains("book1", "book2", "book3", "book4", "book5");
                })
                .verifyComplete();
    }

    @Test
    void shouldFindAllBooks() {
        StepVerifier.create(bookRepository.findAll())
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    void shouldFindCorrectBookById() {
        Mono<Book> monoBook = bookRepository.findById("1");
        StepVerifier.create(monoBook)
                .assertNext((book -> assertThat(book.getTitle()).isEqualTo("book1")))
                .expectComplete()
                .verify();
    }
}
