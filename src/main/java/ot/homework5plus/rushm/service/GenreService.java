package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.domain.Genre;

import java.util.Optional;

public interface GenreService {

    Optional<Genre> findById(long id);

    Genre findByName(String name);

    Genre saveOrUpdate(Genre genre);
}
