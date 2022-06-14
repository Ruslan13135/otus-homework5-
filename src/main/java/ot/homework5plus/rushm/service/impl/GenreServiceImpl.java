package ot.homework5plus.rushm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.domain.Genre;
import ot.homework5plus.rushm.repository.GenreRepository;
import ot.homework5plus.rushm.service.GenreService;

import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    final private GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Optional<Genre> findById(long id) {
        return genreRepository.findById(id);
    }

    @Override
    public Genre findByName(String name) {
        return genreRepository.findByName(name);
    }
}
