package ot.homework5plus.rushm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ot.homework5plus.rushm.domain.Author;

public interface AuthorRepository extends MongoRepository<Author, Long> {

    Author findByName(String name);
}
