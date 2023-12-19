package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Order;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class JoinTest extends EntityManagerTest {

    @Test
    public void joinFetch() {
        String jpql = "select o from Order o "
                + "left join fetch o.payment "
                + "join fetch o.client "
                + "left join fetch o.invoice ";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void leftOuterJoin() {
        String jpql = "select o from Order o left outer join o.payment p on p.status = 'PROCESSING'";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void innerJoin() {
        String jpql = "select o from Order o inner join o.payment p";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

}
