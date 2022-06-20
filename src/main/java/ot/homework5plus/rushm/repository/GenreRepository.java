package ot.homework5plus.rushm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ot.homework5plus.rushm.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findByName(String name);
}
