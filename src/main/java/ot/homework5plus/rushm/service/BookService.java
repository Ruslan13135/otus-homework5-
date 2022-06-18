package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.domain.Book;

import java.util.List;
import java.util.Map;

public interface BookService {

    Book findById(long id);

    Book saveOrUpdate(Book book);

    List<Book> findAll();

    List<Book> findByName(String name);

    void addNewBook();

    long count();

    void deleteById(long id);
}
