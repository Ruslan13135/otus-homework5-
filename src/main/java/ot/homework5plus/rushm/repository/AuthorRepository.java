package ot.homework5plus.rushm.repository;

import ot.homework5plus.rushm.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Author saveOrUpdate(Author author);

    Optional<Author> findById(long id);

    List<Author> findAll();

    Author findByName(String name);
}
