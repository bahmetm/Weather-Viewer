package com.bahmet.weatherviewer.dao;

import com.bahmet.weatherviewer.exception.DatabaseException;
import com.bahmet.weatherviewer.model.Location;
import com.bahmet.weatherviewer.model.User;
import com.bahmet.weatherviewer.util.PersistenceUtil;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class LocationDAO {
    private final EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();

    public void save(Location location) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.persist(location);
//            entityManager.flush();

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            throw new DatabaseException("Database error.");
        } finally {
            entityManager.close();
        }
    }

    public void update(Location location) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.merge(location);
//            entityManager.flush();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DatabaseException("Database error.");
        } finally {
            entityManager.close();
        }
    }

    public Optional<Location> findById(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Location location = entityManager.find(Location.class, id);
            return Optional.ofNullable(location);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new DatabaseException("Database error.");
        } finally {
            entityManager.close();
        }
    }

    public Optional<Location> findByCoordinates(BigDecimal latitude, BigDecimal longitude) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
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
        } finally {
            entityManager.close();
        }
    }


    public Optional<List<Location>> findByUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Location> query = entityManager.createQuery("SELECT l FROM Location l JOIN l.users u WHERE u.id = :userId", Location.class);

        query.setParameter("userId", user.getId());

        try {
            List<Location> locations = query.getResultList();
            return Optional.of(locations);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new DatabaseException("Database error.");
        } finally {
            entityManager.close();
        }
    }

    public void delete(Location location) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.remove(location);
//            entityManager.flush();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DatabaseException("Database error.");
        } finally {
            entityManager.close();
        }
    }
}
