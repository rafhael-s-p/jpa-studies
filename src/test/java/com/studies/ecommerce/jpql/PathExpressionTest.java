package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Order;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class PathExpressionTest extends EntityManagerTest {

    @Test
    public void findOrderWithSpecificProduct() {
        String jpql = "select o from Order o join o.items i where i.id.productId = 1";
//        String jpql = "select o from Order o join o.items i where i.product.id = 1";
//        String jpql = "select o from Order o join o.items i join i.product p where p.id = 1";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void pathExpressions() {
        String jpql = "select o.client.name from Order o";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

}
