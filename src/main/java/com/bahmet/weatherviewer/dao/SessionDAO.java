package com.bahmet.weatherviewer.dao;

import com.bahmet.weatherviewer.model.Session;
import com.bahmet.weatherviewer.util.PersistenceUtil;
import com.bahmet.weatherviewer.exception.DatabaseException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class SessionDAO {
    private final EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();

    public Optional<Session> findById(UUID id) {
        Session session = entityManager.find(Session.class, id);
        return Optional.ofNullable(session);
    }

    public void save(Session session) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            entityManager.persist(session);
            entityManager.flush();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DatabaseException("Database error.");
        }
    }

    public void delete(Session session) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            entityManager.remove(session);
            entityManager.flush();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new DatabaseException("Database error.");
        }
    }

    public void deleteSessionsExpiredAtTime(LocalDateTime dateTime) {
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
        }
    }
}
