package com.studies.ecommerce.criteria;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.*;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;

public class CriteriaGroupByTest extends EntityManagerTest {

    @Test
    public void totalSalesAmongTheCategoriesThatSellTheMost() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<OrderItem> root = criteriaQuery.from(OrderItem.class);
        Join<OrderItem, Product> joinProduct = root.join(OrderItem_.product);
        Join<Product, Category> joinProductCategory = joinProduct.join(Product_.categories);

        criteriaQuery.multiselect(
                joinProductCategory.get(Category_.name),
                criteriaBuilder.sum(root.get(OrderItem_.productPrice)),
                criteriaBuilder.avg(root.get(OrderItem_.productPrice))
        );

        criteriaQuery.groupBy(joinProductCategory.get(Category_.id));

        criteriaQuery.having(criteriaBuilder.greaterThan(
                criteriaBuilder.avg(root.get(OrderItem_.productPrice)).as(BigDecimal.class), new BigDecimal(700)));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();

        list.forEach(arr -> System.out.println(
                "Category Name: " + arr[0]
                        + ", SUM: " + arr[1]
                        + ", AVG: " + arr[2]));
    }

    @Test
    public void totalSalesByMonth() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Order> root = criteriaQuery.from(Order.class);

        Expression<Integer> yearOfOrderCreation = criteriaBuilder
                .function("year", Integer.class, root.get(Order_.createdAt));
        Expression<Integer> monthOfOrderCreation = criteriaBuilder
                .function("month", Integer.class, root.get(Order_.createdAt));
        Expression<String> monthNameOfOrderCreation = criteriaBuilder
                .function("monthname", String.class, root.get(Order_.createdAt));

        Expression<String> yearMonthConcat = criteriaBuilder.concat(
                criteriaBuilder.concat(yearOfOrderCreation.as(String.class), "/"),
                monthNameOfOrderCreation
        );

        criteriaQuery.multiselect(yearMonthConcat, criteriaBuilder.sum(root.get(Order_.total)));

        criteriaQuery.groupBy(yearOfOrderCreation, monthOfOrderCreation);

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();

        list.forEach(arr -> System.out.println("Year/Month: " + arr[0] + ", Sum: " + arr[1]));
    }

    @Test
    public void totalSalesByClient() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<OrderItem> root = criteriaQuery.from(OrderItem.class);
        Join<OrderItem, Order> joinOrder = root.join(OrderItem_.order);
        Join<Order, Client> joinOrderClient = joinOrder.join(Order_.client);

        criteriaQuery.multiselect(
                joinOrderClient.get(Client_.name),
                criteriaBuilder.sum(root.get(OrderItem_.productPrice))
        );

        criteriaQuery.groupBy(joinOrderClient.get(Client_.id));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();

        list.forEach(arr -> System.out.println("Client Name: " + arr[0] + ", Sum: " + arr[1]));
    }

    @Test
    public void totalSalesByCategory() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<OrderItem> root = criteriaQuery.from(OrderItem.class);
        Join<OrderItem, Product> joinProduct = root.join(OrderItem_.product);
        Join<Product, Category> joinProductCategory = joinProduct.join(Product_.categories);

        criteriaQuery.multiselect(
                joinProductCategory.get(Category_.name),
                criteriaBuilder.sum(root.get(OrderItem_.productPrice))
        );

        criteriaQuery.groupBy(joinProductCategory.get(Category_.id));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();

        list.forEach(arr -> System.out.println("Category Name: " + arr[0] + ", Sum: " + arr[1]));

    }

    @Test
    public void productAmountByCategory() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Category> root = criteriaQuery.from(Category.class);
        Join<Category, Product> joinProduto = root.join(Category_.products, JoinType.LEFT);

        criteriaQuery.multiselect(
                root.get(Category_.name),
                criteriaBuilder.count(joinProduto.get(Product_.id))
        );

        criteriaQuery.groupBy(root.get(Category_.id));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();

        list.forEach(arr -> System.out.println("Name: " + arr[0] + ", Count: " + arr[1]));
    }

}
