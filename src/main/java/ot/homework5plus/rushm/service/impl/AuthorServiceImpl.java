package ot.homework5plus.rushm.service.impl;

import org.springframework.stereotype.Service;
import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.repository.AuthorRepository;
import ot.homework5plus.rushm.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author findByName(String authorName) {
        return authorRepository.findByName(authorName);
    }

    @Override
    public Author findById(long id) {
        return authorRepository.findById(id).get();
    }
}
