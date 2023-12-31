package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Client;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.Product;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class SubQueriesTest extends EntityManagerTest {

    @Test
    public void subQueriesWithAny() {

        String allProductsThatHaveAlreadyBeenSoldAtADifferentPriceThanTheCurrentOneJPQL = "select p from Product p " +
                " where p.price <> ANY (select productPrice from OrderItem where product = p)";

        String allProductsThatHaveAlreadyBeenSoldAtLeastOnceAtTheCurrentPriceJPQL = "select p from Product p " +
                " where p.price = ANY (select productPrice from OrderItem where product = p)";

        TypedQuery<Product> allProductsThatHaveAlreadyBeenSoldAtADifferentPriceThanTheCurrentOneTypedQuery
                = entityManager.createQuery(allProductsThatHaveAlreadyBeenSoldAtADifferentPriceThanTheCurrentOneJPQL, Product.class);
        TypedQuery<Product> allProductsThatHaveAlreadyBeenSoldAtLeastOnceAtTheCurrentPriceTypedQuery
                = entityManager.createQuery(allProductsThatHaveAlreadyBeenSoldAtLeastOnceAtTheCurrentPriceJPQL, Product.class);

        List<Product> allProductsThatHaveAlreadyBeenSoldAtADifferentPriceThanTheCurrentOneList =
                allProductsThatHaveAlreadyBeenSoldAtADifferentPriceThanTheCurrentOneTypedQuery.getResultList();
        List<Product> allProductsThatHaveAlreadyBeenSoldAtLeastOnceAtTheCurrentPriceList =
                allProductsThatHaveAlreadyBeenSoldAtLeastOnceAtTheCurrentPriceTypedQuery.getResultList();

        Assert.assertFalse(allProductsThatHaveAlreadyBeenSoldAtADifferentPriceThanTheCurrentOneList.isEmpty());
        Assert.assertFalse(allProductsThatHaveAlreadyBeenSoldAtLeastOnceAtTheCurrentPriceList.isEmpty());

        allProductsThatHaveAlreadyBeenSoldAtADifferentPriceThanTheCurrentOneList.forEach(obj -> System.out.println("JPQL 1 - ID: " + obj.getId()));
        allProductsThatHaveAlreadyBeenSoldAtLeastOnceAtTheCurrentPriceList.forEach(obj -> System.out.println("JPQL 2 - ID: " + obj.getId()));
    }

    @Test
    public void subQueriesWithAll() {

        String allProductsWereNoLongerSoldAfterTheyBecameMoreExpensiveJPQL = "select p from Product p where " +
                " p.price > ALL (select productPrice from OrderItem where product = p)";

        String allProductsThatHaveAlwaysSoldAtTheCurrentPriceJPQL = "select p from Product p where " +
                " p.price = ALL (select productPrice from OrderItem where product = p)";

        String allProductsThatHaveAlwaysBeenSoldAtTheSamePriceJPQL = "select distinct p from OrderItem oi join oi.product p where " +
                " oi.productPrice = ALL (select productPrice from OrderItem where product = p and id <> oi.id)";

        TypedQuery<Product> allProductsWereNoLongerSoldAfterTheyBecameMoreExpensiveTypedQuery =
                entityManager.createQuery(allProductsWereNoLongerSoldAfterTheyBecameMoreExpensiveJPQL, Product.class);
        TypedQuery<Product> allProductsThatHaveAlwaysSoldAtTheCurrentPriceTypedQuery =
                entityManager.createQuery(allProductsThatHaveAlwaysSoldAtTheCurrentPriceJPQL, Product.class);
        TypedQuery<Product> allProductsThatHaveAlwaysBeenSoldAtTheSamePriceTypedQuery =
                entityManager.createQuery(allProductsThatHaveAlwaysBeenSoldAtTheSamePriceJPQL, Product.class);

        List<Product> allProductsWereNoLongerSoldAfterTheyBecameMoreExpensiveList = allProductsWereNoLongerSoldAfterTheyBecameMoreExpensiveTypedQuery.getResultList();
        List<Product> allProductsThatHaveAlwaysSoldAtTheCurrentPriceList = allProductsThatHaveAlwaysSoldAtTheCurrentPriceTypedQuery.getResultList();
        List<Product> allProductsThatHaveAlwaysBeenSoldAtTheSamePriceList = allProductsThatHaveAlwaysBeenSoldAtTheSamePriceTypedQuery.getResultList();

        //Assert.assertFalse(allProductsWereNoLongerSoldAfterTheyBecameMoreExpensiveList.isEmpty());
        Assert.assertFalse(allProductsThatHaveAlwaysSoldAtTheCurrentPriceList.isEmpty());
        Assert.assertFalse(allProductsThatHaveAlwaysBeenSoldAtTheSamePriceList.isEmpty());

        allProductsWereNoLongerSoldAfterTheyBecameMoreExpensiveList.forEach(obj -> System.out.println("JPQL 1 - ID: " + obj.getId()));
        allProductsThatHaveAlwaysSoldAtTheCurrentPriceList.forEach(obj -> System.out.println("JPQL 2 - ID: " + obj.getId()));
        allProductsThatHaveAlwaysBeenSoldAtTheSamePriceList.forEach(obj -> System.out.println("JPQL 3 - ID: " + obj.getId()));
    }

    @Test
    public void subQueriesWithExists() {
        String jpql = "select p from Product p where exists " +
                " (select 1 from OrderItem oi join oi.product p2 where p2 = p)";

        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql, Product.class);

        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void subQueriesWithIn() {
        String jpql = "select o from Order o where o.id in " +
                " (select o2.id from OrderItem oi " +
                "      join oi.order o2 join oi.product p where p.price > 100)";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void subQueries() {
        String goodClients2JPQL = "select c from Client c where " +
                " 500 < (select sum(o.total) from Order o where o.client = c)";

        String goodClients1JPQL = "select c from Client c where " +
                " 500 < (select sum(o.total) from c.orders o)";

        String allOrdersAboveAverageSalesJPQL = "select o from Order o where " +
                " o.total > (select avg(total) from Order)";

        String mostExpensiveDatabaseProductsJPQL = "select p from Product p where " +
                " p.price = (select max(price) from Product)";

        TypedQuery<Client> goodClients2TypedQuery = entityManager.createQuery(goodClients2JPQL, Client.class);
        TypedQuery<Client> goodClients1TypedQuery = entityManager.createQuery(goodClients1JPQL, Client.class);
        TypedQuery<Order> allOrdersAboveAverageSalesTypedQuery = entityManager.createQuery(allOrdersAboveAverageSalesJPQL, Order.class);
        TypedQuery<Product> mostExpensiveDatabaseProductsTypedQuery = entityManager.createQuery(mostExpensiveDatabaseProductsJPQL, Product.class);

        List<Client> goodClients2List = goodClients2TypedQuery.getResultList();
        List<Client> goodClients1List = goodClients1TypedQuery.getResultList();
        List<Order> allOrdersAboveAverageSalesList = allOrdersAboveAverageSalesTypedQuery.getResultList();
        List<Product> mostExpensiveDatabaseProductsList = mostExpensiveDatabaseProductsTypedQuery.getResultList();

        Assert.assertFalse(goodClients2List.isEmpty());
        Assert.assertFalse(goodClients1List.isEmpty());
        Assert.assertFalse(allOrdersAboveAverageSalesList.isEmpty());
        Assert.assertFalse(mostExpensiveDatabaseProductsList.isEmpty());

        goodClients2List.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Name: " + obj.getName()));
        goodClients1List.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Name: " + obj.getName()));
        allOrdersAboveAverageSalesList.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Name: " + obj.getTotal()));
        mostExpensiveDatabaseProductsList.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Name: " + obj.getPrice()));
    }

}
