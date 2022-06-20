package ot.homework5plus.rushm.repository.impl;

import org.springframework.stereotype.Repository;
import ot.homework5plus.rushm.repository.AuthorRepository;
import ot.homework5plus.rushm.domain.Author;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Author saveOrUpdate(Author author) {
        if (author.getId() == null) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book_entity_graph");
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        query.setHint("javax.persistence.fetchgraph",entityGraph);
        return query.getResultList();
    }

    @Override
    public Author findByName(String name) {
        try {
            TypedQuery<Author> query = em.createQuery("select a from Author a where a.name = :name", Author.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}

