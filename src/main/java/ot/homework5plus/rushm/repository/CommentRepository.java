package ot.homework5plus.rushm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;

import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment save(Comment book);

    @Query("select c from Comment c where c.book.id = :id")
    List<Comment> findByBookId(long id);

    @Modifying
    @Query("update Comment c set c.text = :text where c.id = :id")
    void updateTextById(@Param("id") Long id, @Param("text") String text);

    @Query("select c from Comment c left join c.book b where b.author.id = :id")
    List<Comment> findAllCommentsByAuthorId(long id);

    @Modifying
    @Query("delete from Comment c where c.book.id = :id")
    void deleteByBookId(long id);
}
