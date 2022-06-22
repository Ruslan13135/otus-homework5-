package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    void addOrSaveBook(Book book);

    void delete(Book book);
}
