package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.domain.Comment;

import java.util.List;

public interface CommentService {

    Comment save(Comment comment);

    List<Comment> findByBookId(long id);

    void updateTextById(long id, String text);

    void deleteById(long id);

    void addNewComment();

}
