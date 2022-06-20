package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findAllComments(Book book);

    void deleteComment(Comment comment);

    void addOrSaveComment(Comment comment);
}
