package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class FunctionsTest extends EntityManagerTest {

    @Test
    public void nativeFunction() {
        String jpql = "select function('dayname', o.createdAt) from Order o " +
                " where function('average_above_revenue', o.total) = 1";

        TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);

        List<String> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(System.out::println);
    }

    @Test
    public void collectionFunction() {
        String jpql = "select size(o.items) from Order o where size(o.items) > 1";

        TypedQuery<Integer> typedQuery = entityManager.createQuery(jpql, Integer.class);

        List<Integer> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(size -> System.out.println(size));
    }

    @Test
    public void numericFunction() {
        String jpql = "select abs(o.total), mod(o.id, 2), sqrt(o.total) from Order o " +
                " where abs(o.total) > 1000";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
    }

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