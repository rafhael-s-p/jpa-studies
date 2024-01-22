package com.studies.ecommerce.concurrency;

import com.studies.ecommerce.models.Product;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class OptimisticLockTest {

    protected static EntityManagerFactory entityManagerFactory;

    @BeforeClass
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        entityManagerFactory.close();
    }

    private static void log(Object obj, Object... args) {
        System.out.println(
                String.format("[LOG " + System.currentTimeMillis() + "] " + obj, args)
        );
    }

    private static void myWaiting(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {}
    }

    @Test
    public void optimisticLock() {
        Runnable runnable1 = () -> {
            EntityManager entityManager1 = entityManagerFactory.createEntityManager();
            entityManager1.getTransaction().begin();

            log("Runnable 01 will load the product 1.");
            Product product = entityManager1.find(Product.class, 1);

            log("Runnable 01 will wait for 3 seconds.");
            myWaiting(3);

            log("Runnable 01 will update the product.");
            product.setDescription("Detailed Description.");
            product.setUpdatedAt(LocalDateTime.now());

            log("Runnable 01 will confirm the transaction.");
            entityManager1.getTransaction().commit();
            entityManager1.close();
        };

        Runnable runnable2 = () -> {
            EntityManager entityManager2 = entityManagerFactory.createEntityManager();
            entityManager2.getTransaction().begin();

            log("Runnable 02 will load product 1.");
            Product product = entityManager2.find(Product.class, 1);

            log("Runnable 02 will wait for 1 second.");
            myWaiting(1);

            log("Runnable 02 will update the product.");
            product.setDescription("Nice product!");
            product.setUpdatedAt(LocalDateTime.now());

            log("Runnable 02 will confirm the transaction.");
            entityManager2.getTransaction().commit();
            entityManager2.close();
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        EntityManager entityManager3 = entityManagerFactory.createEntityManager();
        Product product = entityManager3.find(Product.class, 1);
        entityManager3.close();

        Assert.assertEquals("Nice product!", product.getDescription());

        log("Terminating test method.");
    }

}
