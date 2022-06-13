package ot.homework5plus.rushm.service.impl;

import org.springframework.stereotype.Service;
import ot.homework5plus.rushm.dao.GenreDao;
import ot.homework5plus.rushm.domain.Genre;
import ot.homework5plus.rushm.service.GenreService;

@Service
public class GenreServiceImpl implements GenreService {
    final private GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Genre getById(long id) {
        return genreDao.getById(id);
    }

    @Override
    public Genre getGenre(String genreName) {
        if (!genreDao.checkByName(genreName)) {
            genreDao.insert(new Genre(genreName));
        }
        return genreDao.getByName(genreName);
    }
}
