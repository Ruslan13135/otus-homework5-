package ot.homework5plus.rushm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.models.entity.Genre;
import ot.homework5plus.rushm.repositories.GenreRepository;
import ot.homework5plus.rushm.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAll() {
        log.debug("The application got in method - getAll()");
        return genreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getGenre(Long genreId) {
        log.debug("The application got in method - getGenre()");
        return genreRepository.findById(genreId).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getGenres(List<String> genreIds) {
        log.debug("The application got in method - getGenres()");
        List<Long> ids = genreIds.stream()
            .map(Long::parseLong)
            .collect(Collectors.toList());
        return genreRepository.findAllById(ids);
    }

    @Override
    @Transactional
    public Genre addGenre(String genreName) {
        log.debug("The application got in method - addGenre()");
        return genreRepository.save(new Genre(0L, genreName));
    }

    @Override
    @Transactional
    public Genre updateGenre(Genre genre) {
        log.debug("The application got in method - updateGenre()");
        return genreRepository.save(genre);
    }

    @Override
    @Transactional
    public void deleteGenre(Long genreId) {
        log.debug("The application got in method - deleteGenre()");
        genreRepository.deleteById(genreId);
    }

    @Override
    public long getCount() {
        log.debug("The application got in method - getCount()");
        return genreRepository.count();
    }
}
