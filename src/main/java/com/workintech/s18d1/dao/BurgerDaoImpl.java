package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BurgerDaoImpl implements BurgerDao {

    private final EntityManager entityManager;

    public BurgerDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Burger save(Burger burger) {
        entityManager.persist(burger);
        return burger;
    }

    @Override
    public Burger findById(Long id) {
        Burger burger = entityManager.find(Burger.class, id);

        if (burger == null) {
            throw new BurgerException(
                    "Burger not found",
                    HttpStatus.NOT_FOUND
            );
        }

        return burger;
    }

    @Override
    public List<Burger> findAll() {
        TypedQuery<Burger> query =
                entityManager.createQuery("FROM Burger", Burger.class);

        return query.getResultList();
    }

    @Override
    public List<Burger> findByPrice(double price) {
        TypedQuery<Burger> query =
                entityManager.createQuery(
                        "FROM Burger WHERE price > :price ORDER BY price DESC",
                        Burger.class
                );

        query.setParameter("price", price);

        return query.getResultList();
    }

    @Override
    public List<Burger> findByBreadType(BreadType breadType) {
        TypedQuery<Burger> query =
                entityManager.createQuery(
                        "FROM Burger WHERE breadType = :breadType ORDER BY name ASC",
                        Burger.class
                );

        query.setParameter("breadType", breadType);

        return query.getResultList();
    }

    @Override
    public List<Burger> findByContent(String content) {
        TypedQuery<Burger> query =
                entityManager.createQuery(
                        "FROM Burger WHERE LOWER(contents) LIKE LOWER(CONCAT('%', :content, '%'))",
                        Burger.class
                );

        query.setParameter("content", content);

        return query.getResultList();
    }

    @Override
    @Transactional
    public Burger update(Burger burger) {
        return entityManager.merge(burger);
    }

    @Override
    @Transactional
    public Burger remove(Long id) {
        Burger burger = findById(id);
        entityManager.remove(burger);
        return burger;
    }
}