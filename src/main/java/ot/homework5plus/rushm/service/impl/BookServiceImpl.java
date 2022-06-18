package ot.homework5plus.rushm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ot.homework5plus.rushm.domain.Comment;
import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Genre;
import ot.homework5plus.rushm.repository.AuthorRepository;
import ot.homework5plus.rushm.repository.BookRepository;
import ot.homework5plus.rushm.repository.CommentRepository;
import ot.homework5plus.rushm.repository.GenreRepository;
import ot.homework5plus.rushm.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public BookServiceImpl(AuthorRepository authorRepository, GenreRepository genreRepository, BookRepository bookRepository, CommentRepository commentRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void addBook(String title, String authorName, String genreName) {
        Author author = authorRepository.findByName(authorName);
        Genre genre = genreRepository.findByName(genreName);
        Book book = new Book(title, author, genre);
        bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(long id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public long count() {
        return bookRepository.count();
    }

    @Override
    public void updateNameById(long id, String name) {
        Book book = bookRepository.findById(id).get();
        book.setTitle(name);
        bookRepository.save(book);
    }

    @Override
    public List<Book> findByName(String name) {
        return bookRepository.findAllByTitle(name);
    }

    @Override
    public void addComment(long bookId, String commentText) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            Comment comment = new Comment(commentText);
            commentRepository.save(comment);
            book.get().setComments(addCommentToBookCommentList(book.get(), comment));
            bookRepository.save(book.get());
        }
    }

    @Override
    public List<Comment> findCommentsByBookId(long id) {
        Book book = bookRepository.findById(id).get();
        return book.getComments();
    }

    @Override
    public List<Book> findAllBooksByAuthorId(long id) {
        return bookRepository.findAllByAuthorId(id);
    }


    private List<Comment> addCommentToBookCommentList(Book book, Comment comment) {
        List<Comment> comments;
        if (book.getComments() == null) {
            comments = new ArrayList<>();
        } else {
            comments = book.getComments();
        }
        comments.add(comment);
        return comments;
    }
}