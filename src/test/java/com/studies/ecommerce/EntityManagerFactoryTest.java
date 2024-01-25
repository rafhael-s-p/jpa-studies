package com.studies.ecommerce;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryTest {

    protected static EntityManagerFactory entityManagerFactory;

    @BeforeClass
    public static void setUpBeforeClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
    }

    @AfterClass
    public static void tearDownAfterClass() {
        entityManagerFactory.close();
    }

    public static void log(Object obj, Object... args) {
        System.out.println(
                String.format("[LOG " + System.currentTimeMillis() + "] " + obj, args)
        );
    }

    public static void myWaiting(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {}
    }

}
