package ot.homework5plus.rushm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;
import ot.homework5plus.rushm.service.AuthorService;
import ot.homework5plus.rushm.service.BookService;
import ot.homework5plus.rushm.service.CommentService;
import ot.homework5plus.rushm.service.IOService;

import java.util.List;

@ShellComponent
public class ShellController {
    private final BookService bookService;
    private final IOService ioService;
    private final CommentService commentService;
    private final AuthorService authorService;

    @Autowired
    public ShellController(BookService bookService, IOService ioService, CommentService commentService, AuthorService authorService) {
        this.bookService = bookService;
        this.ioService = ioService;
        this.commentService = commentService;
        this.authorService = authorService;
    }

    @ShellMethod(value = "show all books", key = {"all-books", "all-b"})
    public void allBooks() {
        List<Book> allBooks = bookService.findAll();
        allBooks.forEach(book -> ioService.write(book.toString()));
    }

    @ShellMethod(value = "add book", key = {"add-book", "ab"})
    public void addBook(@ShellOption("--bookName") String bookName, @ShellOption("--genre") String genre, @ShellOption("--authorName") String authorName) {
        ioService.write("Введите имя, жанр и автора книги");
        bookService.addBook(bookName, authorName, genre);
    }

    @ShellMethod(value = "get book by id", key = {"get-id", "gi"})
    public void getBookById(@ShellOption("--id") Long id) {
        ioService.write(bookService.findById(id).toString());
    }

    @ShellMethod(value = "delete book by id", key = {"delete-book", "db"})
    public void deleteBookById(@ShellOption("--id") Long id) {
        bookService.deleteById(id);
    }

    @ShellMethod(value = "count all books", key = {"count-all", "ca"})
    public void bookCount() {
        ioService.write(bookService.count());
    }

    @ShellMethod(value = "update book name using id", key = {"bookUpdateNameById", "bunbid"})
    public void updateBookNameById(@ShellOption("--id") Long id, @ShellOption("--name") String name) {
        bookService.updateNameById(id, name);
    }

    @ShellMethod(value = "find book using name", key = {"bookFindByName", "bfbn"})
    public void findBookByName(@ShellOption("--name") String name) {
        List<Book> allBooks = bookService.findByName(name);
        allBooks.forEach(book -> ioService.write(book.toString()));
    }

    @ShellMethod(value = "add comment to book using id", key = {"commentAdd", "comment-a"})
    public void addCommentToBookById(@ShellOption("--id") Long id) {
        Book book = bookService.findById(id);
        if (book != null) {
            ioService.write("Введите комментарий для книги - " + book.getTitle());
            String commentText = ioService.read();
            bookService.addComment(id, commentText);
        } else {
            ioService.write("Книги по такому ID не существует.");
        }
    }

    @ShellMethod(value = "show all comments to book using id", key = {"commentShowAll", "csha"})
    public void showAllCommentsToBookById(@ShellOption("--id") Long id) {
        List<Comment> allComments = bookService.findCommentsByBookId(id);
        ioService.write("Комментарии к книге " + bookService.findById(id).getTitle());
        if (allComments != null) {
            allComments.forEach(comment -> ioService.write(comment.toString()));
        } else ioService.write("У книги отсутствуют комментарии");
    }

    @ShellMethod(value = "delete comment using id", key = {"commentDeleteById", "cdbid"})
    public void deleteCommentById(@ShellOption("--id") Long id) {
        commentService.deleteById(id);
    }

    @ShellMethod(value = "show all books by author id", key = {"bookListByAuthorId", "blai"})
    public void showAllBooksByAuthorId(@ShellOption("--id") Long id) {
        List<Book> books = bookService.findAllBooksByAuthorId(id);
        ioService.write("Книги автора: " + authorService.findById(id).getName());
        books.forEach(book -> ioService.write(book.getTitle()));
    }

    @ShellMethod(value = "show all comments to all books by author id", key = {"commentListByAuthorId", "clbai"})
    public void showAllCommentsByAuthorId(@ShellOption("--id") Long id) {
        List<Book> books = bookService.findAllBooksByAuthorId(id);
        ioService.write("Комментарии к книгам автора: " + authorService.findById(id).getName());
        books.forEach(book -> {
            if (book.getComments() != null)
                ioService.write("Книга: " + book.getTitle() + ". Комментарии: " + book.getComments().toString());
            else ioService.write("Книга: " + book.getTitle() + ". Комментарии: 0");
        });
    }

    @ShellMethod(value = "show all books and comments counts", key = {"bookListWithCommentsCountGroupBy", "blwc"})
    public void showAllBooksWithComments() {
        List<Book> books = bookService.findAll();
        books.forEach(author -> {
            ioService.write(author.toStringWithCommentCount());
        });
    }
}
