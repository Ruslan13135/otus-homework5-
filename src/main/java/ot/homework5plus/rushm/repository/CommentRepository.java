package ot.homework5plus.rushm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ot.homework5plus.rushm.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment, Long> {
}
