package ot.homework5plus.rushm.service.impl;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.repository.BookRepository;
import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Genre;
import ot.homework5plus.rushm.service.AuthorService;
import ot.homework5plus.rushm.service.BookService;
import ot.homework5plus.rushm.service.GenreService;
import ot.homework5plus.rushm.service.IOService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    final private IOService ioService;
    final private BookRepository bookRepository;
    final private GenreService genreService;
    final private AuthorService authorService;

    @Autowired
    public BookServiceImpl(IOService ioService, BookRepository bookRepository, GenreService genreService, AuthorService authorService) {
        this.ioService = ioService;
        this.bookRepository = bookRepository;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book findById(long id) {
        return bookRepository.findById(id).get();
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findByName(String name) {
        return bookRepository.findBooksByTitle(name);
    }

    @Transactional
    public void updateNameById(long id, String name) {
        Book book = bookRepository.findById(id).get();
        book.setTitle(name);
        bookRepository.save(book);
    }

    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    public long count() {
        return bookRepository.count();
    }

    public List<Book> findAllBooksByAuthorId(long id) {
        return bookRepository.findAllBooksByAuthorId(id);
    }

    public Map<Book, Long> findAllBooksWithCommentsCount() {
        List<ImmutablePair<Book, Long>> pairList= bookRepository.findAllBooksWithCommentsCount();
        Map<Book, Long> bookMap = new HashMap<>();
        for (ImmutablePair pair: pairList) {
            bookMap.put((Book) pair.left, (long) pair.right);
        }
        return bookMap;
    }

    @Transactional
    public void addNewBook() {
        ioService.write("Пожалуйста, введите название книги");
        String title = ioService.read();
        ioService.write("Пожалуйста, введите жанр");
        String genreName = ioService.read();
        ioService.write("Пожалуйста, введите автора");
        String authorName = ioService.read();
        Author author = authorService.findByName(authorName);
        if (author == null) author = new Author(authorName);
        Genre genre = genreService.findByName(genreName);
        if (genre == null) genre = new Genre(genreName);
        Book book = new Book(title, author, genre);
        bookRepository.save(book);
    }
}