package com.studies.ecommerce.concurrency;

import com.studies.ecommerce.models.Product;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class PessimisticLockTest {

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

    /*
     * Pessimistic Lock on Model Type PessimisticWrite allows every read transaction, but it does not allow transaction commits.
     * If there are concurrent transactions e and only one register is set for PessimisticWrite, the other register will wait until it be committed since there is no @version at the entity.
     * If all the transactions are set for PessimisticWrite, the order will maintain to commit, and @version at the entity is irrelevant.
     * */
    @Test
    public void pessimisticLockPessimisticWriteLockModeType() {
        Runnable runnable1 = () -> {
            log("Starting Runnable 01.");

            String newDescription = "Detailed Description. CTM: " + System.currentTimeMillis();

            EntityManager entityManager1 = entityManagerFactory.createEntityManager();
            entityManager1.getTransaction().begin();

            log("Runnable 01 will load product 1.");
            Product product = entityManager1.find(
                    Product.class, 1, LockModeType.PESSIMISTIC_WRITE);

            log("Runnable 01 will update the product.");
            product.setDescription(newDescription);
            product.setUpdatedAt(LocalDateTime.now());

            log("Runnable 01 will wait for 3 second(s).");
            myWaiting(3);

            log("Runnable 01 will confirm the transaction.");
            entityManager1.getTransaction().commit();
            entityManager1.close();

            log("Finishing Runnable 01.");
        };

        Runnable runnable2 = () -> {
            log("Starting Runnable 02.");

            String newDescription = "Nice Description! CTM: " + System.currentTimeMillis();

            EntityManager entityManager2 = entityManagerFactory.createEntityManager();
            entityManager2.getTransaction().begin();

            log("Runnable 02 will load product 2.");
            Product product = entityManager2.find(
                    Product.class, 1, LockModeType.PESSIMISTIC_WRITE);

            log("Runnable 02 will change the product.");
            product.setDescription(newDescription);
            product.setUpdatedAt(LocalDateTime.now());

            log("Runnable 02 will wait for 1 second(s).");
            myWaiting(1);

            log("Runnable 02 will confirm the transaction.");
            entityManager2.getTransaction().commit();
            entityManager2.close();

            log("Finishing Runnable 02.");
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();

        myWaiting(1);
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

        Assert.assertTrue(product.getDescription().startsWith("Nice Description!"));

        log("Finishing test method.");
    }

    /*
    * Pessimistic Lock on Model Type PessimisticRead allows every read transaction, but it does not allow transaction commits.
    * If there are two locks of this type for the same register, the faster transaction will be committed.
    * */
    @Test
    public void pessimisticLockPessimisticReadLockModeType() {
        Runnable runnable1 = () -> {
            log("Starting Runnable 01.");

            String newDescription = "Detailed Description. CTM: " + System.currentTimeMillis();

            EntityManager entityManager1 = entityManagerFactory.createEntityManager();
            entityManager1.getTransaction().begin();

            log("Runnable 01 will load product 1.");
            Product product = entityManager1.find(
                    Product.class, 1, LockModeType.PESSIMISTIC_READ);

            log("Runnable 01 will update the product.");
            product.setDescription(newDescription);
            product.setUpdatedAt(LocalDateTime.now());

            log("Runnable 01 will wait for 3 second(s).");
            myWaiting(3);

            log("Runnable 01 will confirm the transaction.");
            entityManager1.getTransaction().commit();
            entityManager1.close();

            log("Finishing Runnable 01.");
        };

        Runnable runnable2 = () -> {
            log("Starting Runnable 02.");

            String newDescription = "Nice Description! CTM: " + System.currentTimeMillis();

            EntityManager entityManager2 = entityManagerFactory.createEntityManager();
            entityManager2.getTransaction().begin();

            log("Runnable 02 will load product 2.");
            Product product = entityManager2.find(
                    Product.class, 1, LockModeType.PESSIMISTIC_READ);

            log("Runnable 02 will change the product.");
            product.setDescription(newDescription);
            product.setUpdatedAt(LocalDateTime.now());

            log("Runnable 02 will wait for 1 second(s).");
            myWaiting(1);

            log("Runnable 02 will confirm the transaction.");
            entityManager2.getTransaction().commit();
            entityManager2.close();

            log("Finishing Runnable 02.");
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);

        thread1.start();

        myWaiting(1);
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

        Assert.assertTrue(product.getDescription().startsWith("Nice Description!"));

        log("Finishing test method.");
    }

}
