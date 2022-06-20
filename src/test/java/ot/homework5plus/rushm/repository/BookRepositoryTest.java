package ot.homework5plus.rushm.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.domain.Comment;
import ot.homework5plus.rushm.domain.Genre;
import ot.homework5plus.rushm.domain.Book;

import java.util.ArrayList;
import java.util.List;

@DataMongoTest
class BookRepositoryTest {
    private static final int FIFTH_ID = 5;
    private static final long FOURTH_ID = 4;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void addBook() {
        Genre genre = new Genre(FIFTH_ID, "new Роман");
        genreRepository.save(genre);
        Author author = new Author(FIFTH_ID, "new Роджер Желязны");
        authorRepository.save(author);
        List<Comment> comment = new ArrayList<>();
        comment.add(new Comment(FIFTH_ID, "new Comment"));
        Book book = new Book(FIFTH_ID, "Test", author, genre, comment);
        book = bookRepository.save(book);
        Book actualBook = bookRepository.findById(book.getId()).get();
        Assertions.assertThat(actualBook).isNotNull().matches(b -> !b.getTitle().equals(""))
                .matches(b -> b.getAuthor() != null)
                .matches(b -> b.getGenre() != null);
    }

    @Test
    void findAll() {
        List<Book> books = bookRepository.findAll();
        Assertions.assertThat(books.size()).isEqualTo(FOURTH_ID);
    }

    @Test
    void deleteByBookId() {
        Book firstBook = bookRepository.findById((long) FIFTH_ID).get();
        Assertions.assertThat(firstBook).isNotNull();
        bookRepository.deleteById((long) FIFTH_ID);
        boolean secondBook = bookRepository.findById((long) FIFTH_ID).isEmpty();
        Assertions.assertThat(secondBook).isTrue();
    }

    @Test
    void returnCorrectBookCount() {
        long count = bookRepository.count();
        Assertions.assertThat(count).isEqualTo(4);
    }
}
