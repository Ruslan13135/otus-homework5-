package ot.homework5plus.rushm.repository;

import ot.homework5plus.rushm.domain.Comment;

import java.util.Optional;

public interface CommentRepository {
    Comment saveOrUpdate(Comment comment);

    Optional<Comment> findById(long id);

    void deleteById(long id);

    void deleteByBookId(long id);
}