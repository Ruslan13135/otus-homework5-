package ot.homework5plus.rushm.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Genre;
import ot.homework5plus.rushm.repository.BookRepository;
import ot.homework5plus.rushm.service.AuthorService;
import ot.homework5plus.rushm.service.BookRepositoryService;
import ot.homework5plus.rushm.service.BookService;
import ot.homework5plus.rushm.service.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepositoryService bookRepositoryService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public List<Book> findAll() {
        return bookRepositoryService.findAll();
    }

    @Transactional
    @Override
    public void addBook(Book book) {
        addOrSaveBook(book);
    }


    @Override
    public Book findById(long id) {
        return bookRepositoryService.findById(id);
    }

    @Override
    @Transactional
    public boolean update(long id, Book book) {
        if (bookRepositoryService.findById(id) != null) {
            addOrSaveBook(book);
            return true;
        } else return false;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        bookRepositoryService.deleteBookById(id);
    }

    public void addOrSaveBook(Book book) {
        Author author = authorService.findByName(book.getAuthor().getName());
        if (author == null) author = new Author(book.getAuthor().getName());
        Genre genre = genreService.findByName(book.getGenre().getName());
        if (genre == null) genre = new Genre(book.getGenre().getName());
        book.setAuthor(author);
        book.setGenre(genre);
        bookRepositoryService.save(book);
    }
}