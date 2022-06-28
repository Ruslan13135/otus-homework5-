package ot.homework5plus.rushm.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.repository.BookRepositoryCustom;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    @Override
    public Book findBookByCommentId(long Id) {
        Query query = Query.query(Criteria.where("comments.$id").is(Id));
        return mongoTemplate.findOne(query, Book.class);
    }
}
