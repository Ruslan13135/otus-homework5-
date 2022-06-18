package ot.homework5plus.rushm.service.impl;

import org.springframework.stereotype.Service;
import ot.homework5plus.rushm.domain.Genre;
import ot.homework5plus.rushm.repository.GenreRepository;
import ot.homework5plus.rushm.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre findByName(String genreName) {
        return genreRepository.findByName(genreName);
    }
}
