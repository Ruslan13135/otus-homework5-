package ot.homework5plus.rushm.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ot.homework5plus.rushm.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "author_genre_entity_graph")
    List<Book> findAll();
}
