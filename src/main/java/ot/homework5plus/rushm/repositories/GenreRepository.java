package ot.homework5plus.rushm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ot.homework5plus.rushm.models.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
