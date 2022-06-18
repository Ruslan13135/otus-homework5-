package ot.homework5plus.rushm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ot.homework5plus.rushm.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, Long> {

    List<Book> findAllByTitle(String title);

    List<Book> findAllByAuthorId(long id);
}
