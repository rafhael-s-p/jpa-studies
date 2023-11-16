package com.studies.ecommerce.util;

import com.studies.ecommerce.models.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class StartPersistenceUnit {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Product product = entityManager.find(Product.class, 1);
        System.out.println("Product: " + product.getName());

        entityManager.close();
        entityManagerFactory.close();
    }
}
