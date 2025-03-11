package ru.kata.spring.boot_security.demo.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.*;
import javax.transaction.*;

import org.springframework.stereotype.Repository;

import ru.kata.spring.boot_security.demo.models.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void deleteById(Long id) {
        User user = findById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        EntityGraph<User> entityGraph = entityManager.createEntityGraph(User.class);
        entityGraph.addAttributeNodes("roles");
        
        TypedQuery<User> query = entityManager.createQuery(
            "SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultStream().findFirst();
    }
}
