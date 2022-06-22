package ot.homework5plus.rushm.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ot.homework5plus.rushm.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {

}
