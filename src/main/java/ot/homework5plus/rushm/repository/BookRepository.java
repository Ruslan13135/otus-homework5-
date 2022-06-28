package ot.homework5plus.rushm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ot.homework5plus.rushm.domain.Book;

public interface BookRepository extends MongoRepository<Book, Long>, BookRepositoryCustom {
}
