package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.Product;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ConditionalExpressionsTest extends EntityManagerTest {

    @Test
    public void conditionalExpressionCase() {
        String jpql = "select o.id, " +
                " case type(o.payment) " +
                "       when PaymentSlip then 'Order paid by slip' " +
                "       when PaymentCard then 'Order paid by card' " +
                "       else 'Order not paid yet.' " +
                " end " +
                " from Order o";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void conditionalExpressionNotEqual() {
        String jpql = "select p from Product p where p.price <> 99";

        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql, Product.class);

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void conditionalExpressionBetween() {
        String jpql = "select o from Order o " +
                " where o.createdAt between :initialDate and :finalDate";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        typedQuery.setParameter("initialDate", LocalDateTime.now().minusDays(10));
        typedQuery.setParameter("finalDate", LocalDateTime.now());

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void conditionalExpressionGreaterAndLessWithDate() {
        String jpql = "select o from Order o where o.createdAt > :date";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        typedQuery.setParameter("date", LocalDateTime.now().minusDays(2));

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void conditionalExpressionGreaterAndLess() {
        String jpql = "select p from Product p " +
                " where p.price >= :initialPrice and p.price <= :finalPrice";

        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql, Product.class);
        typedQuery.setParameter("initialPrice", new BigDecimal(399));
        typedQuery.setParameter("finalPrice", new BigDecimal(1499));

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

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
        // String jpql = "select p from Product p where p.categories is empty";
        String jpql = "select p from Product p where p.categories is not empty";

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
