package ot.homework5plus.rushm.service.impl;

import org.springframework.stereotype.Service;
import ot.homework5plus.rushm.dao.AuthorDao;
import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {
    final private AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Author getById(long id) {
        return authorDao.getById(id);
    }

    @Override
    public Author getAuthor(String authorName) {
        if (!authorDao.checkByName(authorName)) authorDao.insert(new Author(authorName));
        return authorDao.getByName(authorName);
    }
}
