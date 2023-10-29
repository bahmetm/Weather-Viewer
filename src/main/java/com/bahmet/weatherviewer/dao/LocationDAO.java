package com.bahmet.weatherviewer.dao;

import com.bahmet.weatherviewer.exception.DatabaseException;
import com.bahmet.weatherviewer.model.Location;
import com.bahmet.weatherviewer.model.User;
import com.bahmet.weatherviewer.util.PersistenceUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Optional;

public class LocationDAO {
    private final EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();

    public void save(Location location) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.persist(location);
            entityManager.flush();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DatabaseException("Database error.");
        }
    }

    public void update(Location location) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.merge(location);
            entityManager.flush();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DatabaseException("Database error.");
        }
    }

    public Optional<Location> findByCoordinates(BigDecimal latitude, BigDecimal longitude) {
        TypedQuery<Location> query = entityManager.createQuery("select l from Location l where l.latitude = :latitude and l.longitude = :longitude", Location.class);

        query.setParameter("latitude", latitude);
        query.setParameter("longitude", longitude);

        try {
            Location location = query.getSingleResult();
            return Optional.of(location);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new DatabaseException("Database error.");
        }
    }

    public void delete(Location location) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.remove(location);
            entityManager.flush();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DatabaseException("Database error.");
        }
    }
}
