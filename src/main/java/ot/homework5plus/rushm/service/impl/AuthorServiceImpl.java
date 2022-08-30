package ot.homework5plus.rushm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.models.entity.Author;
import ot.homework5plus.rushm.repositories.AuthorRepository;
import ot.homework5plus.rushm.service.AuthorService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAll() {
        log.debug("The application got in method - getAll()");
        return authorRepository.findAll();
    }

    public long getCount() {
        log.debug("The application got in method - getCount()");
        return authorRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Author getAuthor(Long authorId) {
        log.debug("The application got in method - getAuthor()");
        return authorRepository.findById(authorId).orElse(null);
    }

    @Override
    @Transactional
    public Author addAuthor(String authorName) {
        log.debug("The application got in method - addAuthor()");
        return authorRepository.save(new Author(0L, authorName));
    }

    @Override
    @Transactional
    public Author updateAuthor(Author author) {
        log.debug("The application got in method - updateAuthor()");
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void deleteAuthor(Long authorId) {
        log.debug("The application got in method - deleteAuthor()");
        authorRepository.deleteById(authorId);
    }

    @Override
    public List<Author> bulkHeadGetAll(Exception e) {
        log.info("The application got in method - bulkHeadGetAll");
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(0L, "NoName"));
        return authors;
    }
}
