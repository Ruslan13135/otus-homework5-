package ot.homework5plus.rushm.repository;

import ot.homework5plus.rushm.domain.Genre;

import java.util.Optional;

public interface GenreRepository {
    Genre saveOrUpdate(Genre genre);

    Optional<Genre> findById(long id);

    Genre findByName(String name);

}
