package ot.homework5plus.rushm.repository;

import ot.homework5plus.rushm.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book saveOrUpdate(Book book);

    Optional<Book> findById(long id);

    List<Book> findAll();

    List<Book> findByName(String name);

    void deleteById(long id);

    long count();
}
