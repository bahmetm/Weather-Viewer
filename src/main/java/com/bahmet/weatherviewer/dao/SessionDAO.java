package com.bahmet.weatherviewer.dao;

import com.bahmet.weatherviewer.model.Session;
import com.bahmet.weatherviewer.util.PersistenceUtil;
import com.bahmet.weatherviewer.exception.DatabaseException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class SessionDAO {
    private final EntityManagerFactory entityManagerFactory = PersistenceUtil.getEntityManagerFactory();

    public Optional<Session> findById(UUID id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Session session = entityManager.find(Session.class, id);
            return Optional.ofNullable(session);
        } catch (Exception e) {
            throw new DatabaseException("Database error.");
        } finally {
            entityManager.close();
        }
    }

    public void save(Session session) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            entityManager.persist(session);
//            entityManager.flush();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DatabaseException("Database error.");
        } finally {
            entityManager.close();
        }
    }

    public void delete(Session session) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            if (!entityManager.contains(session)) {
                // If it's not managed, then merge it into the current persistence context.
                session = entityManager.merge(session);
            }

            entityManager.remove(session);
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

    public void deleteExpiredSessions() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        LocalDateTime dateTime = LocalDateTime.now();

        Query query = entityManager.createQuery("delete from Session s where s.expiresAt <= :dateTime");
        query.setParameter("dateTime", dateTime);

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            query.executeUpdate();
            entityManager.flush();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DatabaseException("Database error.");
        } finally {
            entityManager.close();
        }
    }
}
