package ot.homework5plus.rushm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBook(Book book);
}
