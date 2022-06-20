package ot.homework5plus.rushm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ot.homework5plus.rushm.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);
}
