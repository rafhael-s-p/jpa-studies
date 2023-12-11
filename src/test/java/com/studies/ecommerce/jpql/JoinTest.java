package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class JoinTest extends EntityManagerTest {

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
