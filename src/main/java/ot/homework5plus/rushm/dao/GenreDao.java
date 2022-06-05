package ot.homework5plus.rushm.dao;

import org.springframework.stereotype.Repository;
import ot.homework5plus.rushm.domain.Genre;

import java.util.Optional;

@Repository
public interface GenreDao {

    Genre save(Genre genre);

    Optional<Genre> findById(long id);

    Genre findByName(String name);
}
