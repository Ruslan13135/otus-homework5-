package ot.homework5plus.rushm.dao;

import org.springframework.stereotype.Repository;
import ot.homework5plus.rushm.domain.Genre;

@Repository
public interface GenreDao {

    Genre getById(long id);

    Genre getByName(String genreName);

    boolean checkByName(String genreName);

    void insert(Genre genre);
}
