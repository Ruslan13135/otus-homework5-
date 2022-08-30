package ot.homework5plus.rushm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ot.homework5plus.rushm.models.entity.Book;
import ot.homework5plus.rushm.models.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBook(Book book); // мб удалить или поправить

    List<Comment> findByBookAndFavorite(Book book, boolean favorite);
}
