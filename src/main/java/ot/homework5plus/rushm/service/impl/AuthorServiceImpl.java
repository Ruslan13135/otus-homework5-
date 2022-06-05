package ot.homework5plus.rushm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.dao.AuthorDao;
import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.service.AuthorService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    final private AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    @Transactional
    public Author save(Author author) {
        return authorDao.save(author);
    }

    @Override
    public Author findById(long id) {
        return authorDao.findById(id).get();
    }

    @Override
    public List<Author> findAll() {
        return authorDao.findAll();
    }

    @Override
    public Author findByName(String name) {
        return authorDao.findByName(name);
    }
}
