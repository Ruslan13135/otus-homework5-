package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.domain.Book;

import java.util.List;

public interface BookRepositoryService {

    List<Book> findAll();

    Book findById(Long id);

    void deleteBookById(Long id);

    void save(Book book);
}
