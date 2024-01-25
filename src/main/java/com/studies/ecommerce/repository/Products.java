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

    public Product find(Integer id) {
        return entityManager.find(Product.class, id);
    }

    public Product save(Product product) {
        return entityManager.merge(product);
    }

    public List<Product> list() {
        return entityManager
                .createQuery("select p from Product p", Product.class)
                .getResultList();
    }

}
