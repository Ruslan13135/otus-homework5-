package ot.homework5plus.rushm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.service.BookService;
import ot.homework5plus.rushm.service.IOService;

import java.util.List;

@ShellComponent
public class ShellController {
    private final BookService bookService;
    private final IOService ioService;

    @Autowired
    public ShellController(BookService bookService, IOService ioService) {
        this.bookService = bookService;
        this.ioService = ioService;
    }

    @ShellMethod(value = "show all books", key = {"all-books", "all-b"})
    public void allBooks() {
        List<Book> allBooks = bookService.getAll();
        allBooks.forEach(book -> ioService.write(book.toString()));
    }

    @ShellMethod(value = "add book", key = {"add-book", "ab"})
    public void addBook() {
        Book book = bookService.getNewBook();
        bookService.insert(book);
    }

    @ShellMethod(value = "get book by id", key = {"get-id", "gi"})
    public void getBookById(@ShellOption("--id") Long id) {
        ioService.write(bookService.getById(id).toString());
    }

    @ShellMethod(value = "delete book by id", key = {"delete-book", "db"})
    public void deleteBookById(@ShellOption("--id") Long id) {
        bookService.deleteById(id);
    }

    @ShellMethod(value = "count all books", key = {"count-all", "ca"})
    public void bookCount() {
        ioService.write(bookService.getCount());
    }
}
