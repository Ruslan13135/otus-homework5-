package ot.homework5plus.rushm.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.service.impl.BookServiceImpl;

import java.util.List;

@DataJpaTest
@Import({BookServiceImpl.class, AuthorService.class})
@Transactional(propagation = Propagation.REQUIRES_NEW)
class BookServiceImplTest {

    @Autowired
    BookServiceImpl bookService;

    @Autowired
    AuthorService authorService;

    @Test
    void getBooks() {
        List<Book> actualBook = bookService.findAll();
        Assertions.assertThat(actualBook.get(0).getTitle()).isEqualTo("Игра престолов");
        Assertions.assertThat(actualBook.get(1).getTitle()).isEqualTo("Девять принцев Амбера");
        Assertions.assertThat(actualBook.get(2).getTitle()).isEqualTo("Война и мир");
    }

}