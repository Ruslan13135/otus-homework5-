package ot.homework5plus.rushm.controllers;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ot.homework5plus.rushm.models.entity.*;
import ot.homework5plus.rushm.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class BookController {

    private static final String BOOK_SERVICE = "bookService";

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Получить все книги или книги содержащие в названии searchText
    @GetMapping("/api/book")
    @Bulkhead(name = BOOK_SERVICE, fallbackMethod = "bulkHeadGetAllBook", type = Type.SEMAPHORE)
    public ResponseEntity<List<Book>> getBooks(@RequestParam("search") Optional<String> search) {
        List<Book> books;
        if (search.isPresent()) {
            books = bookService.getBooksLikeName(search.get());
        } else {
            books = bookService.getAll();
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Получить книгу по id
    @GetMapping("/api/book/{id}")
    @Bulkhead(name = BOOK_SERVICE, fallbackMethod = "bulkHeadGetBook", type = Type.SEMAPHORE)
    public ResponseEntity<Book> getBook(@PathVariable("id") Long bookId) {
        Book book = bookService.getBook(bookId);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // Создать новую книгу
    @PostMapping("/api/book")
    @Bulkhead(name = BOOK_SERVICE, fallbackMethod = "bulkHeadGetBook", type = Type.SEMAPHORE)
    public ResponseEntity<Book> addBook(@RequestBody Book requestBook) {
        if (requestBook == null || requestBook.getName() == null || requestBook.getName().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        requestBook.setId(null);
        Book book = bookService.addBook(requestBook);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // Обновить книгу
    @PutMapping("/api/book/{id}")
    @Bulkhead(name = BOOK_SERVICE, fallbackMethod = "bulkHeadGetBook", type = Type.SEMAPHORE)
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book requestBook) {
        if (requestBook == null || requestBook.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        requestBook.setId(id);
        Book book = bookService.getBook(requestBook.getId());
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        book = bookService.updateBook(requestBook);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // Удалить книгу
    @DeleteMapping("/api/book/{id}")
    @Bulkhead(name = BOOK_SERVICE, fallbackMethod = "bulkHeadDelBook", type = Type.SEMAPHORE)
    public ResponseEntity<String> deleteBook(@PathVariable("id") Long bookId) {
        if (bookId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Book book = bookService.getBook(bookId);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<Book>> bulkHeadGetAllBook(Exception t) {
        log.info("bulkHeadGetAllBook");
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Название книги",
                new Author(1L, "Автор Книги"),
                List.of(new Genre(1L, "Жанр Книги")),
                List.of(new Order()),
                List.of(new Instance())));
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    public ResponseEntity<Book> bulkHeadGetBook(Exception t) {
        log.info("bulkHeadGetBook");
        Book book = new Book(1L, "Название книги",
                new Author(1L, "Автор Книги"),
                List.of(new Genre(1L, "Жанр Книги")),
                List.of(new Order()),
                List.of(new Instance()));
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    public ResponseEntity<String> bulkHeadDelBook(Exception t) {
        log.info("bulkHeadDelBook");
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
