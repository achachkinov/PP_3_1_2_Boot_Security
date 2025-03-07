package ru.kata.spring.boot_security.demo.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.transaction.*;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("FROM Role", Role.class).getResultList();
    }
 
    @Override
    public List<Role> findRolesById(List<Long> rolesId) {
        TypedQuery<Role> query = entityManager.createQuery(
            "SELECT r FROM Role r WHERE r.id IN :rolesId", Role.class);
        query.setParameter("rolesId", rolesId);
        return query.getResultList();
    }
}
