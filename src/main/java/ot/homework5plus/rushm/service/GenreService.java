package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.domain.Genre;

public interface GenreService {

    Genre getById(long id);

    Genre getGenre(String genreName);
}
