package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.domain.Author;

import java.util.List;

public interface AuthorService {

    Author findByName(String authorName);

    List<Author> findAll();
}
