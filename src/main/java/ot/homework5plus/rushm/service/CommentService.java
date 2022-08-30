package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.models.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getCommentsByBookId(Long bookId);

    List<Comment> getFavoriteCommentsByBookId(Long bookId);

    Comment getComment(Long commentId);

    Comment addComment(Comment comment);

    Comment updateComment(Comment comment);

    void deleteComment(Long commentId);

    long getCount();
}
