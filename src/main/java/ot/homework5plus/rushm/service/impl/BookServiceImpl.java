package ot.homework5plus.rushm.service.impl;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ot.homework5plus.rushm.dao.BookDao;
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
    final private BookDao bookDao;
    final private GenreService genreService;
    final private AuthorService authorService;

    @Autowired
    public BookServiceImpl(IOService ioService, BookDao bookDao, GenreService genreService, AuthorService authorService) {
        this.ioService = ioService;
        this.bookDao = bookDao;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @Override
    @Transactional
    public Book save(Book book) {
        return bookDao.save(book);
    }

    @Override
    public Book findById(long id) {
        return bookDao.findById(id).get();
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public List<Book> findByName(String name) {
        return bookDao.findByName(name);
    }

    @Override
    @Transactional
    public void updateNameById(long id, String name) {
        bookDao.updateNameById(id, name);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }

    @Override
    public long getCount() {
        return bookDao.getCount();
    }

    @Override
    public List<Book> findAllBooksByAuthorId(long id) {
        return bookDao.findAllBooksByAuthorId(id);
    }

    @Override
    public List<Book> findAllWithComments() {
        return bookDao.findAllWithComments();
    }

    @Override
    public Map<Book, Long> findAllBooksWithCommentsCount() {
        List<ImmutablePair<Book, Long>> pairList= bookDao.findAllBooksWithCommentsCount();
        Map<Book, Long> bookMap = new HashMap<>();
        for (ImmutablePair pair: pairList) {
            bookMap.put((Book) pair.left, (long) pair.right);
        }
        return bookMap;
    }

    @Override
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
        bookDao.save(book);
    }
}