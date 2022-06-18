package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;

import java.util.List;

public interface BookService {

    Book findById(long id);

    List<Book> findAll();

    List<Book> findByName(String name);

    List<Comment> findCommentsByBookId(long id);

    List<Book> findAllBooksByAuthorId(long id);

    void addBook(String title, String authorName, String genreName);

    void deleteById(long id);

    long count();

    void updateNameById(long id, String name);

    void addComment(long bookId, String commentText);
}
