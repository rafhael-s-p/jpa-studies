package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class ConditionalExpressionsTest extends EntityManagerTest {

    @Test
    public void conditionalExpressionIsNull() {
        String jpql = "select p from Product p where p.productPhoto is null";
        // String jpql = "select p from Product p where p.productPhoto is not null";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void conditionalExpressionIsEmpty() {
        String jpql = "select p from Product p where p.categories is empty";
        // String jpql = "select p from Product p where p.categories is not empty";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void conditionalExpressionLike() {
        String jpql = "select c from Client c where c.name like concat('%', :name, '%')";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        typedQuery.setParameter("name", "Esther");

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

}
