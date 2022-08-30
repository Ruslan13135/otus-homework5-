package ot.homework5plus.rushm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.models.entity.Book;
import ot.homework5plus.rushm.models.entity.Comment;
import ot.homework5plus.rushm.repositories.BookRepository;
import ot.homework5plus.rushm.repositories.CommentRepository;
import ot.homework5plus.rushm.service.CommentService;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentServiceImpl(
        CommentRepository commentRepository,
        BookRepository bookRepository
    ) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByBookId(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return Collections.emptyList();
        }
        return commentRepository.findByBook(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getFavoriteCommentsByBookId(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            return Collections.emptyList();
        }
        return commentRepository.findByBookAndFavorite(book, true);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getComment(Long commentId) {
        log.debug("The application got in method - getComment()");
        return commentRepository.findById(commentId).orElse(null);
    }

    @Override
    @Transactional
    public Comment addComment(Comment comment) {
        log.debug("The application got in method - addComment()");
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment updateComment(Comment comment) {
        log.debug("The application got in method - updateComment()");
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        log.debug("The application got in method - deleteComment()");
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getCount() {
        log.debug("The application got in method - getCount()");
        return commentRepository.count();
    }
}
