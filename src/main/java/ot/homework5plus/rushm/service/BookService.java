package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    Book getById(long id);

    Book getNewBook();

    int getCount();

    void insert(Book book);

    void deleteById(long id);
}
