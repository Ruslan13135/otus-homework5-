package ot.homework5plus.rushm.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import ot.homework5plus.rushm.domain.*;
import ot.homework5plus.rushm.repository.AuthorRepository;
import ot.homework5plus.rushm.repository.BookRepository;
import ot.homework5plus.rushm.repository.CommentRepository;
import ot.homework5plus.rushm.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class DatabaseChangeLog {

    @ChangeSet(order = "001", id = "addMoreBooks", author = "Ruslan")
    public void insertBooks(AuthorRepository authorRepository, BookRepository bookRepository, CommentRepository commentRepository, GenreRepository genreRepository) {
        Genre genre1 = new Genre(1, "genre1");
        Genre genre2 = new Genre(2, "genre2");
        Author author1 = new Author(1, "author1");
        Author author2 = new Author(2, "author2");
        Author author3 = new Author(3, "author3");
        Comment comment1 = new Comment(1, "new comment 1");
        Comment comment2 = new Comment(2, "comment 2");
        Comment comment3 = new Comment(3, "comment 3");
        Comment comment4 = new Comment(4, "comment 4");
        List<Comment> list1 = new ArrayList();
        List<Comment> list2 = new ArrayList();
        list1.add(comment1);
        list1.add(comment2);
        list2.add(comment3);
        list2.add(comment4);

        Book book1 = new Book(1, "book1", author1, genre1, list1);
        Book book2 = new Book(2, "book2", author1, genre2, list2);
        Book book3 = new Book(3, "book3", author2, genre1, null);
        Book book4 = new Book(4, "book4", author2, genre2, null);
        Book book5 = new Book(5, "book5", author3, genre2, null);

        genreRepository.save(genre1);
        genreRepository.save(genre2);
        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);
        bookRepository.save(book5);
    }
}
