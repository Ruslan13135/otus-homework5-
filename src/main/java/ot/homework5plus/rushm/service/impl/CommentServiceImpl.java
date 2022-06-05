package ot.homework5plus.rushm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.dao.CommentDao;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;
import ot.homework5plus.rushm.service.BookService;
import ot.homework5plus.rushm.service.CommentService;
import ot.homework5plus.rushm.service.IOService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    final private CommentDao commentDao;
    final private IOService ioService;
    final private BookService bookService;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, IOService ioService, BookService bookService) {
        this.commentDao = commentDao;
        this.ioService = ioService;
        this.bookService = bookService;
    }

    @Override
    @Transactional
    public Comment save(Comment comment) {
        return commentDao.save(comment);
    }

    @Override
    public List<Comment> findByBookId(long id) {
        return commentDao.findByBookId(id);
    }

    @Override
    @Transactional
    public void updateTextById(long id, String text) {
        commentDao.updateTextById(id, text);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentDao.deleteById(id);
    }

    @Override
    @Transactional
    public void addNewComment() {
        ioService.write("Пожалуйста, введите id книги, которой вы бы хотели добавить комментарий");
        int bookId = ioService.readInt();
        Book book = bookService.findById(bookId);
        if (book != null) {
            ioService.write("Введите комментарий для книги: " + book.getTitle());
            String commentText = ioService.read();
            Comment comment = new Comment(commentText, book);
            commentDao.save(comment);
        } else {
            ioService.write("Книги по такому уникальному идентификатору не существует");
        }
    }

    @Override
    public List<Comment> findAllCommentsByAuthorId(long id) {
        return commentDao.findAllCommentsByAuthorId(id);
    }

    @Override
    @Transactional
    public void deleteByBookId(long id) {
        commentDao.deleteByBookId(id);
    }
}
