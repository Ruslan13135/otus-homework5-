package ot.homework5plus.rushm.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ot.homework5plus.rushm.dao.impl.BookDaoImpl;
import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Genre;

import java.util.ArrayList;
import java.util.List;


@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(BookDaoImpl.class)
class BookDaoImplTest {
    private static final int EXPECTED_BOOKS_COUNT = 3;
    private static final long NEW_BOOK_ID = 4;
    private static final long GET_BOOK_ID = 1;

    @Autowired
    private BookDaoImpl bookDao;

    @Test
    void shouldReturnCorrectBookCount() {
        Assertions.assertThat(bookDao.getCount()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @Test
    void insertBook() {
        Book book = new Book(NEW_BOOK_ID, "Test", new Author(2L,"Роджер Желязны"), new Genre(3L,"Роман"));
        bookDao.insert(book);
        Book actualBook = bookDao.getById(NEW_BOOK_ID);
        Assertions.assertThat(actualBook.getTitle()).isEqualTo(book.getTitle());
        Assertions.assertThat(actualBook.getGenre()).isEqualTo(book.getGenre());
        Assertions.assertThat(actualBook.getAuthor()).isEqualTo(book.getAuthor());
    }

    @Test
    void returnCorrectBookById() {
        Book book = new Book(GET_BOOK_ID, "Игра престолов", new Author(1L, "Джордж Мартин"), new Genre(1L, "Фэнтези"));
        Book actualBook = bookDao.getById(GET_BOOK_ID);
        Assertions.assertThat(actualBook).isEqualTo(book);
    }

    @Test
    void returnCorrectBookList() {
        Book book1 = new Book(1L, "Игра престолов", new Author(1L, "Джордж Мартин"), new Genre(1L, "Фэнтези"));
        Book book2 = new Book(2L, "Девять принцев Амбера", new Author(2L, "Роджер Желязны"), new Genre(2L, "Фантастика"));
        Book book3 = new Book(3L, "Война и мир", new Author(3L, "Лев Толстой"), new Genre(3L, "Роман"));
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        List<Book> actualBooks = bookDao.getAll();
        Assertions.assertThat(actualBooks).isEqualTo(books);
    }

}
