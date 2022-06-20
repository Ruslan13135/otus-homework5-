package ot.homework5plus.rushm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ot.homework5plus.rushm.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, Long> {

    Genre findByName(String name);
}
