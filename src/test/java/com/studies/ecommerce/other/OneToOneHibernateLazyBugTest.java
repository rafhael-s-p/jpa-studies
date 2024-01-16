package com.studies.ecommerce.other;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Order;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class OneToOneHibernateLazyBugTest extends EntityManagerTest {

    @Test
    public void oneToOneHibernateLazyBug() {
        System.out.println("Finding Order:");
        Order order = entityManager.find(Order.class, 1);
        Assert.assertNotNull(order);

        System.out.println("----------------------------------------------------");

        System.out.println("Finding Order List:");
        List<Order> list = entityManager
                .createQuery("select o from Order o", Order.class)
                .getResultList();
        Assert.assertFalse(list.isEmpty());
    }

}
