package ot.homework5plus.rushm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ot.homework5plus.rushm.domain.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>  {

    Genre findByName(String name);
}
