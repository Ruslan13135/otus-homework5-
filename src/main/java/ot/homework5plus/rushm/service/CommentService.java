package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.domain.Comment;

import java.util.List;

public interface CommentService {

    Comment saveOrUpdate(Comment comment);

    Comment findById(long id);

    void deleteById(long id);

    void addNewComment();

    void updateTextById(long id, String text);
}
