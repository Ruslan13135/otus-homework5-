package ot.homework5plus.rushm.dao;

import ot.homework5plus.rushm.domain.Author;

public interface AuthorDao {

    Author getById(long id);

    Author getByName(String genreName);

    boolean checkByName(String genreName);

    void insert(Author genre);
}
