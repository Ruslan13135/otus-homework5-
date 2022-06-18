package ot.homework5plus.rushm.repository.impl;

import org.springframework.stereotype.Repository;
import ot.homework5plus.rushm.domain.Comment;
import ot.homework5plus.rushm.repository.CommentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment saveOrUpdate(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public void deleteById(long id) {
        Comment comment = em.find(Comment.class, id);
        em.remove(comment);
    }

    @Override
    public void deleteByBookId(long id) {
        Query query = em.createQuery("delete " +
                "from Comment c " +
                "where c.book.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
