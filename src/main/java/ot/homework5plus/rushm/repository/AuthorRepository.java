package ot.homework5plus.rushm.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ot.homework5plus.rushm.domain.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

}
