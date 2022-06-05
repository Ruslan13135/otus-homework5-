package ot.homework5plus.rushm.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ot.homework5plus.rushm.dao.impl.BookDaoImpl;
import ot.homework5plus.rushm.dao.impl.CommentDaoImpl;
import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Genre;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({BookDaoImpl.class, CommentDaoImpl.class})
class BookDaoImplTest {
    private static final long THREE_ID = 3;
    private static final long SECOND_ID = 2;
    private static final long FIRST_ID = 1;
    private static final long ZERO_ID = 0;

    @Autowired
    private BookDaoImpl bookDao;

    @Autowired
    private CommentDaoImpl commentDao;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldReturnCorrectBookCount() {
        Assertions.assertThat(bookDao.getCount()).isEqualTo(THREE_ID);
    }

    @Test
    void insertBook() {
        Author author = new Author(SECOND_ID,"Роджер Желязны");
        Genre genre = new Genre(THREE_ID,"Роман");
        Book book = new Book("Test", author, genre);
        book = bookDao.save(book);
        Book actualBook = em.find(Book.class, book.getId());
        Assertions.assertThat(actualBook).isNotNull().matches(b -> !b.getTitle().equals(""))
                .matches(b -> b.getAuthor() != null)
                .matches(b -> b.getGenre() != null);
    }

    @Test
    void returnCorrectBookById() {
        Optional<Book> optionalActualBook = bookDao.findById(FIRST_ID);
        Book expectedBook = em.find(Book.class, FIRST_ID);
        Assertions.assertThat(optionalActualBook).isPresent().get().isEqualTo(expectedBook);
    }

    @Test
    void returnCorrectBookList() {
        List<Book> books = bookDao.findAll();
        Assertions.assertThat(books).isNotNull().hasSize((int) THREE_ID)
                .allMatch(book -> !book.getTitle().equals(""))
                .allMatch(book -> book.getGenre() != null)
                .allMatch(book -> book.getAuthor() != null);
    }

    @Test
    void findBookByName() {
        Book firstBook = em.find(Book.class, FIRST_ID);
        List<Book> books = bookDao.findByName("Игра престолов");
        Assertions.assertThat(books).containsOnlyOnce(firstBook);
    }

    @Test
    void updateBookNameById() {
        Book firstBook = em.find(Book.class, FIRST_ID);
        String oldName = firstBook.getTitle();
        em.clear();
        bookDao.updateNameById(FIRST_ID, "Test");
        Book updatedBook = em.find(Book.class, FIRST_ID);
        Assertions.assertThat(updatedBook.getTitle()).isNotEqualTo(oldName).isEqualTo("Test");
    }

    @Test
    void deleteBookNameById() {
        em.clear();
        commentDao.deleteByBookId(FIRST_ID);
        bookDao.deleteById(FIRST_ID);
        Book deletedBook = em.find(Book.class, FIRST_ID);
        Assertions.assertThat(deletedBook).isNull();
    }

    @Test
    void returnCorrectBookCount() {
        long count = bookDao.getCount();
        Assertions.assertThat(count).isEqualTo(THREE_ID);
    }
}
