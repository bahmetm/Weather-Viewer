package com.bahmet.weatherviewer.dao;

import com.bahmet.weatherviewer.exception.DatabaseException;
import com.bahmet.weatherviewer.exception.UserExistsException;
import com.bahmet.weatherviewer.model.User;
import com.bahmet.weatherviewer.util.PersistenceUtil;

import javax.persistence.*;
import java.util.Optional;

public class UserDAO {
    private final EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();

    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.username = :username", User.class);
        query.setParameter("username", username);

        try {
            User user = query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new DatabaseException("Database error.");
        }
    }

    public void save(User user) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.persist(user);
            entityManager.flush();

            transaction.commit();
        } catch (EntityExistsException e) {
            transaction.rollback();
            throw new UserExistsException("User with this username already exists.");
        } catch (Exception e) {
            transaction.rollback();
            throw new DatabaseException("Database error.");
        }
    }
}
