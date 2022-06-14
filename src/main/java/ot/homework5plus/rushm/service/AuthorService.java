package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.domain.Author;

import java.util.List;

public interface AuthorService {

    Author save(Author author);

    Author findById(long id);

    List<Author> findAll();

    Author findByName(String name);
}
