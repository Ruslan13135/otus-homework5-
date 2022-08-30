package ot.homework5plus.rushm.service;

import ot.homework5plus.rushm.models.entity.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> getAll();

    Genre getGenre(Long genreId);

    List<Genre> getGenres(List<String> genreIds);

    Genre addGenre(String genreName);

    Genre updateGenre(Genre genre);

    void deleteGenre(Long genreId);

    long getCount();
}
