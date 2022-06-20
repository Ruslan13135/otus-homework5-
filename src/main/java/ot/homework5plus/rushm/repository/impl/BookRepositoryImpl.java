package ot.homework5plus.rushm.repository.impl;

import org.springframework.stereotype.Repository;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.repository.BookRepository;


import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book saveOrUpdate(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("author_genre_entity_graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph",entityGraph);
        return query.getResultList();
    }

    @Override
    public List<Book> findByName(String title) {
        EntityGraph<?> entityGraph = em.getEntityGraph("author_genre_entity_graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.title = :title", Book.class);
        query.setParameter("title", title);
        query.setHint("javax.persistence.fetchgraph",entityGraph);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Book book = em.find(Book.class, id);
        em.remove(book);
    }

    @Override
    public long count() {
        return em.createQuery("select count(b) from Book b", Long.class).getSingleResult();
    }

}
