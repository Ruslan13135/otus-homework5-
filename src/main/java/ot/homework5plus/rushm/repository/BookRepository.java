package ot.homework5plus.rushm.repository;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ot.homework5plus.rushm.domain.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "author_genre_entity_graph")
    List<Book> findBooksByTitle(String title);

    @EntityGraph(value = "author_genre_entity_graph")
    List<Book> findAll();

    @Query("select b from Book b where b.author.id = :id")
    List<Book> findAllBooksByAuthorId(long id);

    @Query("select new org.apache.commons.lang3.tuple.ImmutablePair (b, count(c))"+
            "                        from Book b left join Comment c on b.id = c.book.id "+
            "                        group by c.book")

    List<ImmutablePair<Book,Long>> findAllBooksWithCommentsCount();

    @Modifying
    @Query("update Book b set b.title = :name where b.id = :id")
    void updateNameById(@Param("id") Long id, @Param("name") String name);
}
