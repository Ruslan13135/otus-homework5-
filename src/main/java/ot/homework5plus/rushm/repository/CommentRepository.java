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

    List<Comment> findById(long id);
}
