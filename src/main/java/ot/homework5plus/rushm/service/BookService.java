package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    void addBook(Book book);

    Book findById(long id);

    boolean update(long id, Book book);

    void deleteById(Long id);
}
