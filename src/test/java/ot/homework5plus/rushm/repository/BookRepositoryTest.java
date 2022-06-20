package ot.homework5plus.rushm.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Genre;

import java.util.ArrayList;
import java.util.List;


@DataJpaTest
class BookRepositoryTest {
    private static final int THREE_ID = 3;
    private static final long SECOND_ID = 2;
    private static final long FIRST_ID = 1;
    private static final long ZERO_ID = 0;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldReturnCorrectBookCount() {
        Assertions.assertThat(bookRepository.count()).isEqualTo(THREE_ID);
    }

    @Test
    void insertBook() {
        Book book = new Book("Test", new Author("Роджер Желязны2"), new Genre("Роман2"));
        bookRepository.save(book);
        Book actualBook = bookRepository.findById(FIRST_ID).get();
        Assertions.assertThat(actualBook.getTitle()).isEqualTo(book.getTitle());
        Assertions.assertThat(actualBook.getGenre()).isEqualTo(book.getGenre());
        Assertions.assertThat(actualBook.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    void returnCorrectBookById() {
        Book book = new Book(SECOND_ID, "Игра престолов", new Author("Джордж Мартин"), new Genre("Фэнтези"));
        Book actualBook = bookRepository.findById(SECOND_ID).get();
        Assertions.assertThat(actualBook.getAuthor().getName()).isEqualTo(book.getAuthor().getName());
        Assertions.assertThat(actualBook.getTitle()).isEqualTo(book.getTitle());
        Assertions.assertThat(actualBook.getId()).isEqualTo(book.getId());
    }

    @Test
    void returnCorrectBookList() {
        Book book1 = new Book(FIRST_ID, "Игра престолов", new Author("Джордж Мартин"), new Genre("Фэнтези"));
        Book book2 = new Book(SECOND_ID, "Девять принцев Амбера", new Author("Роджер Желязны"), new Genre("Фантастика"));
        Book book3 = new Book(THREE_ID, "Война и мир", new Author("Лев Толстой"), new Genre("Роман"));
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        List<Book> actualBooks = bookRepository.findAll();
        Assertions.assertThat(actualBooks.get((int) ZERO_ID).getTitle()).isEqualTo(books.get((int) ZERO_ID).getTitle());
        Assertions.assertThat(actualBooks.get((int) FIRST_ID).getTitle()).isEqualTo(books.get((int) FIRST_ID).getTitle());
        Assertions.assertThat(actualBooks.get((int) SECOND_ID).getTitle()).isEqualTo(books.get((int) SECOND_ID).getTitle());
    }

}
