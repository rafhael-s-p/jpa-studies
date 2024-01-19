package com.studies.ecommerce.cache;

import com.studies.ecommerce.models.Order;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

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

    private static void myWaiting(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {}
    }

    private static void log(Object obj) {
        System.out.println("[LOG " + System.currentTimeMillis() + "] " + obj);
    }

    @Test
    public void ehcache() {
        Cache cache = entityManagerFactory.getCache();

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        log("Finding and including on cache...");
        entityManager1
                .createQuery("select o from Order o", Order.class)
                .getResultList();
        log("---");

        myWaiting(1);
        Assert.assertTrue(cache.contains(Order.class, 2));
        entityManager2.find(Order.class, 2);

        myWaiting(3);
        Assert.assertFalse(cache.contains(Order.class, 2));
    }

    @Test
    public void manageCacheDynamically() {
        // javax.persistence.cache.retrieveMode CacheRetrieveMode
        // javax.persistence.cache.storeMode CacheStoreMode

        Cache cache = entityManagerFactory.getCache();

        System.out.println("Find all orders..........................");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        entityManager1.setProperty("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS);
        entityManager1
                .createQuery("select o from Order o", Order.class)
                .setHint("javax.persistence.cache.storeMode", CacheStoreMode.USE)
                .getResultList();

        System.out.println("Find order of ID 2..................");
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        Map<String, Object> properties = new HashMap<>();
//        properties.put("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS);
//        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        entityManager2.find(Order.class, 2, properties);

        System.out.println("Find all orders (again)..........................");
        EntityManager entityManager3 = entityManagerFactory.createEntityManager();
        entityManager3
                .createQuery("select o from Order o", Order.class)
//                .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
                .getResultList();
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
