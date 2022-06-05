package ot.homework5plus.rushm.dao;

import ot.homework5plus.rushm.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    Author save (Author author);

    Optional<Author> findById(long id);

    List<Author> findAll();

    Author findByName(String name);
}
