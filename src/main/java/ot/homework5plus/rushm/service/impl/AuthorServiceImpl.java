package ot.homework5plus.rushm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.repository.AuthorRepository;
import ot.homework5plus.rushm.service.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public Author findByName(String authorName) {
        return authorRepository.findByName(authorName).orElse(null);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
