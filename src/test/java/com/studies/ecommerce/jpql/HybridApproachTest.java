package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Category;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class HybridApproachTest extends EntityManagerTest {

    @BeforeClass
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");

        EntityManager em = entityManagerFactory.createEntityManager();

        String jpql = "select c from Category c";
        TypedQuery<Category> typedQuery = em.createQuery(jpql, Category.class);

        entityManagerFactory.addNamedQuery("Category.list", typedQuery);
    }

    @Test
    public void hybridApproach() {
        TypedQuery<Category> typedQuery = entityManager
                .createNamedQuery("Category.list", Category.class);

        List<Category> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

}