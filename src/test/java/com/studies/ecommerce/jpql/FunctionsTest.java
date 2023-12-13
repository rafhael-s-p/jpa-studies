package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class FunctionsTest extends EntityManagerTest {

    @Test
    public void dateFunction() {
        // TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        // current_date, current_time, current_timestamp
        // year(o.createdAt), month(o.createdAt), day(o.createdAt)

        String jpql = "select hour(o.createdAt), minute(o.createdAt), second(o.createdAt) " +
                " from Order o where hour(o.createdAt) > 1";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
    }

    @Test
    public void stringFunction() {
        // concat, length, locate, substring, lower, upper, trim

        String jpql = "select c.name, length(c.name) from Category c " +
                " where substring(c.name, 1, 1) = 'S'";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));
    }

}