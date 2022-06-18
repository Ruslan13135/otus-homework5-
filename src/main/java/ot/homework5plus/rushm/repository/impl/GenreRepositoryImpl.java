package ot.homework5plus.rushm.repository.impl;

import org.springframework.stereotype.Repository;
import ot.homework5plus.rushm.domain.Genre;
import ot.homework5plus.rushm.repository.GenreRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class GenreRepositoryImpl implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Genre saveOrUpdate(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public Genre findByName(String name) {
        try {
            TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name = :name", Genre.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
