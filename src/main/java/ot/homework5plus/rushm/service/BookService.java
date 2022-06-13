package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.domain.Book;

import java.util.List;
import java.util.Map;

public interface BookService {

    Book findById(long id);

    Book save(Book book);

    List<Book> findAll();

    List<Book> findByName(String name);

    List<Book> findAllBooksByAuthorId(long id);

    Map<Book, Long> findAllBooksWithCommentsCount();

    void updateNameById(long id, String name);

    void addNewBook();

    long count();

    void deleteById(long id);
}
