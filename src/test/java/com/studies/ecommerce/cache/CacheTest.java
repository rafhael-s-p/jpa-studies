package com.studies.ecommerce.cache;

import com.studies.ecommerce.models.Order;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CacheTest {

    protected static EntityManagerFactory entityManagerFactory;

    @BeforeClass
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        entityManagerFactory.close();
    }

    @Test
    public void checkCacheOptions() {
        Cache cache = entityManagerFactory.getCache();

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        System.out.println("Find from instance 1:");
        entityManager1
                .createQuery("select o from Order o", Order.class)
                .getResultList();

        Assert.assertTrue(cache.contains(Order.class, 1));
    }

    @Test
    public void checkCacheEntity() {
        Cache cache = entityManagerFactory.getCache();

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();

        System.out.println("Find from instance 1:");
        entityManager1
                .createQuery("select o from Order o", Order.class)
                .getResultList();

        Assert.assertTrue(cache.contains(Order.class, 1));
        Assert.assertTrue(cache.contains(Order.class, 2));
    }

    @Test
    public void removingCache() {
        Cache cache = entityManagerFactory.getCache();

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Find from instance 1:");
        entityManager1
                .createQuery("select o from Order o", Order.class)
                .getResultList();

        System.out.println("Removing from cache");
        cache.evictAll(); // Remove all cache
//        cache.evict(Order.class); // Remove only the selected entity from cache
//        cache.evict(Order.class, 1); // Remove only the selected register from cache

        System.out.println("Find from instance 2:");
        entityManager2.find(Order.class, 1);
        entityManager2.find(Order.class, 2);
    }

    @Test
    public void addOrderEntityOnCache() {
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Find from instance 1:");
        entityManager1
                .createQuery("select o from Order o", Order.class)
                .getResultList();

        System.out.println("Find from instance 2:");
        entityManager2.find(Order.class, 1);
    }

    @Test
    public void findFromCache() {
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        System.out.println("Find from instance 1:");
        entityManager1.find(Order.class, 1);

        System.out.println("Find from instance 1 (First Level Cache):");
        entityManager1.find(Order.class, 1);

        System.out.println("Find from instance 2 (Second Level Cache):");
        entityManager2.find(Order.class, 1);
    }

}
