package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.domain.Author;

public interface AuthorService {

    Author findByName(String authorName);
}
