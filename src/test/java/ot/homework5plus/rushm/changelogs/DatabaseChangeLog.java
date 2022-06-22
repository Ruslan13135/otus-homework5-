package ot.homework5plus.rushm.changelogs;

import io.changock.migration.api.annotations.ChangeLog;
import io.changock.migration.api.annotations.ChangeSet;
import ot.homework5plus.rushm.domain.Author;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;
import ot.homework5plus.rushm.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class DatabaseChangeLog {

    @ChangeSet(order = "001", id = "addMoreBooks", author = "Ruslan")
    public void insertBooks(AuthorMongoRepository authorRepository, BookMongoRepository bookRepository, CommentMongoRepository commentRepository, GenreMongoRepository genreRepository) {
        Genre genre1 = new Genre("genre1");
        Genre genre2 = new Genre("genre2");
        Author author1 = new Author("author1");
        Author author2 = new Author("author2");
        Author author3 = new Author("author3");
        Comment comment1 = new Comment("new comment 1");
        Comment comment2 = new Comment("comment 2");
        Comment comment3 = new Comment("comment 3");
        Comment comment4 = new Comment("comment 4");
        List<Comment> list1 = new ArrayList();
        List<Comment> list2 = new ArrayList();
        list1.add(comment1);
        list1.add(comment2);
        list2.add(comment3);
        list2.add(comment4);

        Book book1 = new Book("1", "book1", author1, genre1, list1);
        Book book2 = new Book("2", "book2", author1, genre2, list2);
        Book book3 = new Book("3", "book3", author2, genre1, null);
        Book book4 = new Book("4", "book4", author2, genre2, null);
        Book book5 = new Book("5", "book5", author3, genre2, null);

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
