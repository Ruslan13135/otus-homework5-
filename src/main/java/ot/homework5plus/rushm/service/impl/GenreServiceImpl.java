package ot.homework5plus.rushm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.dao.GenreDao;
import ot.homework5plus.rushm.domain.Genre;
import ot.homework5plus.rushm.service.GenreService;

import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    final private GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    @Transactional
    public Genre save(Genre genre) {
        return genreDao.save(genre);
    }

    @Override
    public Optional<Genre> findById(long id) {
        return genreDao.findById(id);
    }

    @Override
    public Genre findByName(String name) {
        return genreDao.findByName(name);
    }
}
