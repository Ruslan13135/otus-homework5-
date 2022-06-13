package ot.homework5plus.rushm.dao;

import ot.homework5plus.rushm.domain.Book;

import java.util.List;

public interface BookDao {

    List<Book> getAll();

    Book getById(long id);

    int getCount();

    long insert(Book book);

    void deleteById(long id);
}
