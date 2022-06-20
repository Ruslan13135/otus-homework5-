package ot.homework5plus.rushm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;
import ot.homework5plus.rushm.repository.CommentRepository;
import ot.homework5plus.rushm.service.CommentService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findAllComments(Book book) {
        return commentRepository.findAllByBook(book);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public void addOrSaveComment(Comment comment) {
        commentRepository.save(comment);
    }
}
