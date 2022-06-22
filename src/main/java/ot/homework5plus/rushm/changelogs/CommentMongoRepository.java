package ot.homework5plus.rushm.changelogs;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ot.homework5plus.rushm.domain.Comment;

@Repository
public interface CommentMongoRepository extends MongoRepository<Comment, String> {

}
