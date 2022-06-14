package ot.homework5plus.rushm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ot.homework5plus.rushm.domain.Author;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @EntityGraph(value = "book_entity_graph")
    List<Author> findAll();

    Author findByName(String name);
}
