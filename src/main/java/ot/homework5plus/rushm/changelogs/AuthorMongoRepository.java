package ot.homework5plus.rushm.changelogs;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ot.homework5plus.rushm.domain.Author;

@Repository
public interface AuthorMongoRepository extends MongoRepository<Author, String> {

}
