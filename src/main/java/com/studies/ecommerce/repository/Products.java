package com.studies.ecommerce.repository;

import com.studies.ecommerce.models.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class Products {

    @PersistenceContext
    private EntityManager entityManager;

    public Product find(Integer id, String tenant) {
        return entityManager
                .createQuery("select p from Product p where p.id = :id and p.tenant = :tenant",
                        Product.class)
                .setParameter("id", id)
                .setParameter("tenant", tenant)
                .getSingleResult();
    }

    public Product save(Product product) {
        return entityManager.merge(product);
    }

    public List<Product> list(String tenant) {
        return entityManager
                .createQuery("select p from Product p where p.tenant = :tenant", Product.class)
                .setParameter("tenant", tenant)
                .getResultList();
    }

}
