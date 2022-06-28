package ot.homework5plus.rushm.changelogs;

import com.mongodb.client.MongoDatabase;
import io.changock.migration.api.annotations.ChangeLog;
import io.changock.migration.api.annotations.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import ot.homework5plus.rushm.domain.*;

import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class DatabaseChangeLog {

    @ChangeSet(order = "000", id = "dropDatabase", author = "Ruslan")
    public void dropDatabase(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "addMoreBooks", author = "Ruslan")
    public void insertBooks(MongockTemplate repository) {
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

        List<Book> author1books = new ArrayList<>();
        author1books.add(book1);
        author1books.add(book2);
        List<Book> author2books = new ArrayList<>();
        author2books.add(book3);
        author2books.add(book4);
        List<Book> author3books = new ArrayList<>();
        author3books.add(book5);

        repository.save(genre1);
        repository.save(genre2);
        repository.save(author1);
        repository.save(author2);
        repository.save(author3);
        repository.save(comment1);
        repository.save(comment2);
        repository.save(comment3);
        repository.save(comment4);
        repository.save(book1);
        repository.save(book2);
        repository.save(book3);
        repository.save(book4);
        repository.save(book5);

        DatabaseSequence authorsSequence = new DatabaseSequence("authors_sequence", 3);
        DatabaseSequence genresSequence = new DatabaseSequence("genres_sequence", 2);
        DatabaseSequence booksSequence = new DatabaseSequence("books_sequence", 5);
        DatabaseSequence commentSequence = new DatabaseSequence("comments_sequence", 4);

        repository.save(authorsSequence);
        repository.save(genresSequence);
        repository.save(booksSequence);
        repository.save(commentSequence);
    }
}
