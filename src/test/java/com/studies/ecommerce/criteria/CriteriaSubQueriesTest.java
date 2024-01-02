package com.studies.ecommerce.criteria;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.*;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.math.BigDecimal;
import java.util.List;

public class CriteriaSubQueriesTest extends EntityManagerTest {

    @Test
    public void clientsThatBoughtMoreThan1300() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Order> subqueryRoot = subquery.from(Order.class);
        subquery.select(criteriaBuilder.sum(subqueryRoot.get(Order_.total)));
        subquery.where(criteriaBuilder.equal(root, subqueryRoot.get(Order_.client)));

        criteriaQuery.where(criteriaBuilder.greaterThan(subquery, new BigDecimal(1300)));

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Client> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Name: " + obj.getName()));
    }

    @Test
    public void allOrdersAboveAverageSales() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Order> subqueryRoot = subquery.from(Order.class);
        subquery.select(criteriaBuilder.avg(subqueryRoot.get(Order_.total)).as(BigDecimal.class));

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Order_.total), subquery));

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Total: " + obj.getTotal()));
    }

    @Test
    public void theMostExpensiveProductOrProducts() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Product> subqueryRoot = subquery.from(Product.class);
        subquery.select(criteriaBuilder.max(subqueryRoot.get(Product_.price)));

        criteriaQuery.where(criteriaBuilder.equal(root.get(Product_.price), subquery));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Name: " + obj.getName() + ", Price: " + obj.getPrice()));
    }

}
