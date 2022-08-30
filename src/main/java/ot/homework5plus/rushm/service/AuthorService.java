package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.models.entity.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAll();

    Author getAuthor(Long authorId);

    Author addAuthor(String authorName);

    Author updateAuthor(Author author);

    void deleteAuthor(Long authorId);

    long getCount();

    List<Author> bulkHeadGetAll(Exception e);
}
