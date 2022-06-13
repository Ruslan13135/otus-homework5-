package ot.homework5plus.rushm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;
import ot.homework5plus.rushm.service.AuthorService;
import ot.homework5plus.rushm.service.BookService;
import ot.homework5plus.rushm.service.CommentService;
import ot.homework5plus.rushm.service.IOService;

import java.util.List;
import java.util.Map;

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
    public void addBook() {
        bookService.addNewBook();
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
        Book book = bookService.findById(id);
        book.setTitle(name);
        bookService.save(book);
    }

    @ShellMethod(value = "find book using name", key = {"bookFindByName", "bfbn"})
    public void findBookByName(@ShellOption("--name") String name) {
        List<Book> allBooks = bookService.findByName(name);
        allBooks.forEach(book -> ioService.write(book.toString()));
    }

    @ShellMethod(value = "add comment to book using id", key = {"commentAdd", "comment-a"})
    public void addCommentToBookById() {
        commentService.addNewComment();
    }

    @ShellMethod(value = "show all comments to book using id", key = {"commentShowAll", "csha"})
    public void showAllCommentsToBookById(@ShellOption("--id") Long id) {
        List<Comment> allComments = commentService.findByBookId(id);
        ioService.write("Комментарии к книге " + bookService.findById(id).getTitle());
        allComments.forEach(comment -> ioService.write(comment.toString()));
    }

    @ShellMethod(value = "delete comment using id", key = {"commentDeleteById", "cdbid"})
    public void deleteCommentById(@ShellOption("--id") Long id) {
        commentService.deleteById(id);
    }

    @ShellMethod(value = "edit comment text using id", key = {"commentEditTextByID", "cetbid"})
    public void editCommentById(@ShellOption("--id") Long id, @ShellOption("--text") String text) {
        commentService.updateTextById(id, text);
    }

    @ShellMethod(value = "show all authors and book counts", key = {"authorList", "al"})
    public void showAllAuthors() {
        List<Author> authors = authorService.findAll();
        authors.forEach(author -> ioService.write(author.toString()));
    }

    @ShellMethod(value = "show all books using author id", key = {"bookListByAuthorId", "blai"})
    public void showAllBooksByAuthorId(@ShellOption("--id") Long id) {
        List<Book> books = bookService.findAllBooksByAuthorId(id);
        ioService.write("Книги автора: " + authorService.findById(id).getName());
        books.forEach(book -> ioService.write(book.getTitle()));
    }

    @ShellMethod(value = "show all comments to all books using author id", key = {"commentListByAuthorId", "clbai"})
    public void showAllCommentsByAuthorId(@ShellOption("--id") Long id) {
        List<Comment> comments = commentService.findAllCommentsByAuthorId(id);
        ioService.write("Комментарии к книгам автора: " + authorService.findById(id).getName());
        comments.forEach(comment -> ioService.write("Книга: " + comment.getBook().getTitle() + ". Комментарий: " + comment.getText()));
    }

    @ShellMethod(value = "show all books and comments counts", key = {"bookListWithCommentsCountGroupBy", "blwc"})
    public void showAllBooksWithComments() {
        Map<Book, Long> books = bookService.findAllBooksWithCommentsCount();
        for(Map.Entry<Book, Long> entry: books.entrySet()){
            ioService.write(entry.getKey().toString());
            ioService.write("Количество комментариев: " + entry.getValue());
        }
    }
}
