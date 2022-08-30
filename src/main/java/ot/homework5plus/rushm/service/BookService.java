package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.models.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    Book getBook(Long bookId);

    List<Book> getBooksLikeName(String substring);

    Book getBookByCommentId(String commentId);

    Book addBook(Book book);

    Book updateBook(Book book);

    void deleteBook(Long bookId);

    long getCount();
}
