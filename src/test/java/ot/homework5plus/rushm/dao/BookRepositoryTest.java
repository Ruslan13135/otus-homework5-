package ot.homework5plus.rushm.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ot.homework5plus.rushm.repository.BookRepository;
import ot.homework5plus.rushm.repository.CommentRepository;
import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Genre;

import java.util.List;
import java.util.Optional;


@DataJpaTest
class BookRepositoryTest {
    private static final long THREE_ID = 3;
    private static final long SECOND_ID = 2;
    private static final long FIRST_ID = 1;
    private static final long ZERO_ID = 0;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldReturnCorrectBookCount() {
        Assertions.assertThat(bookRepository.count()).isEqualTo(THREE_ID);
    }

    @Test
    void insertBook() {
        Author author = new Author("new Роджер Желязны");
        Genre genre = new Genre("new Роман");
        Book book = new Book("Test", author, genre);
        book = bookRepository.save(book);
        Book actualBook = em.find(Book.class, book.getId());
        Assertions.assertThat(actualBook).isNotNull().matches(b -> !b.getTitle().equals(""))
                .matches(b -> b.getAuthor() != null)
                .matches(b -> b.getGenre() != null);
    }

    @Test
    void returnCorrectBookById() {
        Optional<Book> optionalActualBook = bookRepository.findById(SECOND_ID);
        Book expectedBook = em.find(Book.class, SECOND_ID);
        Assertions.assertThat(optionalActualBook).isPresent().get().isEqualTo(expectedBook);
    }

    @Test
    void returnCorrectBookList() {
        List<Book> books = bookRepository.findAll();
        Assertions.assertThat(books).isNotNull().hasSize((int) THREE_ID)
                .allMatch(book -> !book.getTitle().equals(""))
                .allMatch(book -> book.getGenre() != null)
                .allMatch(book -> book.getAuthor() != null);
    }

    @Test
    void findBookByName() {
        Book firstBook = em.find(Book.class, SECOND_ID);
        List<Book> books = bookRepository.findBooksByTitle("Игра престолов");
        Assertions.assertThat(books).containsOnlyOnce(firstBook);
    }

    @Test
    void updateBookNameById() {
        Book firstBook = em.find(Book.class, SECOND_ID);
        String oldName = firstBook.getTitle();
        em.clear();
        Book book = bookRepository.findById(SECOND_ID).get();
        book.setTitle("Test");
        bookRepository.save(book);
        Book updatedBook = em.find(Book.class, SECOND_ID);
        Assertions.assertThat(updatedBook.getTitle()).isNotEqualTo(oldName).isEqualTo("Test");
    }

    @Test
    void deleteBookNameById() {
        em.clear();
        commentRepository.deleteById(SECOND_ID);
        bookRepository.deleteById(SECOND_ID);
        Book deletedBook = em.find(Book.class, SECOND_ID);
        Assertions.assertThat(deletedBook).isNull();
    }

    @Test
    void returnCorrectBookCount() {
        long count = bookRepository.count();
        Assertions.assertThat(count).isEqualTo(THREE_ID);
    }
}
