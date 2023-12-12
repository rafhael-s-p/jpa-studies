package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Order;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class LogicalOperatorsTest extends EntityManagerTest {

    @Test
    public void operators() {
        String jpql = "select o from Order o " +
                " where (o.status = 'WAITING' or o.status = 'PAID') and o.total > 100";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

}
