package ot.homework5plus.rushm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.repository.CommentRepository;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;
import ot.homework5plus.rushm.service.CommentService;
import ot.homework5plus.rushm.service.IOService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    final private CommentRepository commentRepository;
    final private IOService ioService;
    final private BookServiceImpl bookService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, IOService ioService, BookServiceImpl bookService) {
        this.commentRepository = commentRepository;
        this.ioService = ioService;
        this.bookService = bookService;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findByBookId(long id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional
    public void updateTextById(long id, String text) {
        List<Comment> commentById = commentRepository.findById(id);
        commentById.forEach(comment -> comment.setText(text));
        commentRepository.saveAll(commentById);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
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
            commentRepository.save(comment);
        } else {
            ioService.write("Книги по такому уникальному идентификатору не существует");
        }
    }

    @Transactional
    public void deleteByBookId(long id) {
        commentRepository.deleteById(id);
    }
}
