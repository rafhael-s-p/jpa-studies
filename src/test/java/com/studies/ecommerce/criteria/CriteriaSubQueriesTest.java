package com.studies.ecommerce.criteria;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.*;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;

public class CriteriaSubQueriesTest extends EntityManagerTest {

    @Test
    public void allProductsThatHaveAlwaysBeenSoldAtTheSamePrice() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<OrderItem> root = criteriaQuery.from(OrderItem.class);

        criteriaQuery.select(root.get(OrderItem_.product));
        criteriaQuery.distinct(true);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
        subquery.select(subqueryRoot.get(OrderItem_.productPrice));
        subquery.where(
                criteriaBuilder.equal(subqueryRoot.get(OrderItem_.product), root.get(OrderItem_.product)),
                criteriaBuilder.notEqual(subqueryRoot, root)
        );

        criteriaQuery.where(criteriaBuilder.equal(root.get(OrderItem_.productPrice), criteriaBuilder.all(subquery)));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void allProductsThatHaveAlreadyBeenSoldAtADifferentPriceThanTheCurrentOne() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
        subquery.select(subqueryRoot.get(OrderItem_.productPrice));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(OrderItem_.product), root));

        criteriaQuery.where(criteriaBuilder.notEqual(root.get(Product_.price), criteriaBuilder.any(subquery)));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void allProductsThatHaveAlreadyBeenSoldAtLeastOnceAtTheCurrentPrice() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
        subquery.select(subqueryRoot.get(OrderItem_.productPrice));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(OrderItem_.product), root));

        criteriaQuery.where(criteriaBuilder.equal(root.get(Product_.price), criteriaBuilder.any(subquery)));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void allProductsWereNoLongerSoldAfterTheyBecameMoreExpensive() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
        subquery.select(subqueryRoot.get(OrderItem_.productPrice));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(OrderItem_.product), root));

        criteriaQuery.where(
                criteriaBuilder.greaterThan(
                        root.get(Product_.price), criteriaBuilder.all(subquery)),
                criteriaBuilder.exists(subquery)
        );

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void allProductsThatHaveAlwaysBeenSoldAtTheCurrentPrice() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
        subquery.select(subqueryRoot.get(OrderItem_.productPrice));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(OrderItem_.product), root));

        criteriaQuery.where(criteriaBuilder.equal(root.get(Product_.price), criteriaBuilder.all(subquery)));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void allProductsThatHaveAlreadyBeenSoldAtAPriceDifferentFromTheCurrentPrice() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(
                criteriaBuilder.equal(subqueryRoot.get(OrderItem_.product), root),
                criteriaBuilder.notEqual(subqueryRoot.get(OrderItem_.productPrice), root.get(Product_.price))
        );

        criteriaQuery.where(criteriaBuilder.exists(subquery));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void allOrdersThatHaveAProductWithId2() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
        Join<OrderItem, Product> subQueryJoinProduct = subqueryRoot.join(OrderItem_.product);
        Join<Product, Category> subQueryJoinProductCategories = subQueryJoinProduct.join(Product_.categories);
        subquery.select(subqueryRoot.get(OrderItem_.id).get(OrderItemId_.orderId));
        subquery.where(criteriaBuilder.equal(subQueryJoinProductCategories.get(Category_.id), 2));

        criteriaQuery.where(root.get(Order_.id).in(subquery));

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void allClientsWhoPlacedMoreThanTwoOrders() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.select(root);

        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Order> subqueryRoot = subquery.from(Order.class);
        subquery.select(criteriaBuilder.count(criteriaBuilder.literal(1)));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(Order_.client), root));

        criteriaQuery.where(criteriaBuilder.greaterThan(subquery, 2L));

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Client> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Name: " + obj.getName()));
    }

    @Test
    public void allProductsThatHaveAlreadyBeenSold() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get(OrderItem_.product), root));

        criteriaQuery.where(criteriaBuilder.exists(subquery));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void productWithPriceGreaterThan100() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
        Join<OrderItem, Order> subQueryJoinOrder = subqueryRoot.join(OrderItem_.order);
        Join<OrderItem, Product> subQueryJoinProduct = subqueryRoot.join(OrderItem_.product);
        subquery.select(subQueryJoinOrder.get(Order_.id));
        subquery.where(criteriaBuilder.greaterThan(subQueryJoinProduct.get(Product_.price), new BigDecimal(100)));

        criteriaQuery.where(root.get(Order_.id).in(subquery));

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

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
