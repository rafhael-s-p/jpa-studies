package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Order;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;

public class JPQLBasicTest extends EntityManagerTest {

    @Test
    public void findById() {
        TypedQuery<Order> typedQuery = entityManager
                .createQuery("select o from Order o where o.id = 1", Order.class);
        Order order = typedQuery.getSingleResult();
        Assert.assertNotNull(order);
    }

}
