package ot.homework5plus.rushm.service.impl;

import org.springframework.stereotype.Service;
import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;
import ot.homework5plus.rushm.domain.Genre;
import ot.homework5plus.rushm.service.CachedDataService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CachedDataServiceImpl implements CachedDataService {

    @Override
    public List<Book> getCachedBooks() {
        List<Book> books = new ArrayList<>();
        books.add(cachedBook());
        return books;
    }

    @Override
    public Book getCachedBook() {
        return cachedBook();
    }

    @Override
    public List<Comment> getCachedComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1L, "cachedComment"));
        return comments;
    }


    private Book cachedBook(){
        Author author = new Author(1L, "cachedAuthorName");
        Genre genre = new Genre(1L, "cachedGenreName");
        return new Book(1L, "cachedBookTitle", author, genre);
    }
}
