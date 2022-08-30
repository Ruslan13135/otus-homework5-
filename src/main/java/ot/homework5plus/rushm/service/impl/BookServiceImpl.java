package ot.homework5plus.rushm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.models.entity.Book;
import ot.homework5plus.rushm.models.entity.Comment;
import ot.homework5plus.rushm.repositories.BookRepository;
import ot.homework5plus.rushm.repositories.CommentRepository;
import ot.homework5plus.rushm.service.BookService;
import ot.homework5plus.rushm.service.CommentService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    public BookServiceImpl(
        BookRepository bookRepository,
        CommentRepository commentRepository,
        CommentService commentService
    ) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.commentService = commentService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        log.debug("The application got in method - getAll()");
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBook(Long bookId) {
        log.debug("The application got in method - getBook()");
        return bookRepository.findById(bookId).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksLikeName(String substring) {
        return bookRepository.findBookByNameContainingIgnoreCase(substring);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookByCommentId(String commentId) {
        log.debug("The application got in method - getBookByCommentId()");
        Optional<Comment> comment = commentRepository.findById(Long.parseLong(commentId));
        return comment.map(Comment::getBook).orElse(null);
    }

    @Override
    @Transactional
    public Book addBook(Book book) {
        log.debug("The application got in method - addBook()");
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        log.debug("The application got in method - updateBook()");
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        log.debug("The application got in method - deleteBook()");
        bookRepository.deleteById(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getCount() {
        log.debug("The application got in method - getCount()");
        return bookRepository.count();
    }
}
