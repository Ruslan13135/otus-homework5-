package ot.homework5plus.rushm.dao;

import ot.homework5plus.rushm.domain.Comment;

import java.util.List;

public interface CommentDao {

    Comment save(Comment comment);

    List<Comment> findByBookId(long id);

    List<Comment> findAllCommentsByAuthorId(long id);

    void updateTextById(long id, String text);

    void deleteById(long id);

    void deleteByBookId(long id);
}