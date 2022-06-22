package ot.homework5plus.rushm.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ot.homework5plus.rushm.domain.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

}
