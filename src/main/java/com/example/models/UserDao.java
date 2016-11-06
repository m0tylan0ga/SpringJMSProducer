package com.example.models;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(User user) {
        entityManager.persist(user);
    }

    public void delete(User user) {
        if (entityManager.contains(user))
            entityManager.remove(user);
    }

    public List<User> getAll() {
        return entityManager.createQuery("from User").getResultList();
    }

    public User getByEmail(String email) {
        return (User) entityManager.createQuery("from User where email = :email").setParameter("email", email)
                .getSingleResult();
    }

    public User getById(long id) {
        return entityManager.find(User.class, id);
    }

    public void update(User user) {
        entityManager.merge(user);
    }

}
