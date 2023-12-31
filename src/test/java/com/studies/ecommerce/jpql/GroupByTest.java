package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class GroupByTest extends EntityManagerTest {

    @Test
    public void groupingWithHaving() {

        String totalSalesAmongCategoriesThatSellTheMostJPQL = "select c.name, sum(oi.productPrice) from OrderItem oi " +
                " join oi.product p join p.categories c " +
                " group by c.id " +
                " having avg(oi.productPrice) > 1500 ";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(totalSalesAmongCategoriesThatSellTheMostJPQL, Object[].class);

        List<Object[]> list = typedQuery.getResultList();

        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void groupAndFilterResult() {
        String totalSalesByMonthJPQL = "select concat(year(o.createdAt), '/', function('monthname', o.createdAt)), sum(o.total) " +
                " from Order o " +
                " where year(o.createdAt) = year(current_date) " +
                " group by year(o.createdAt), month(o.createdAt) ";

        String totalSalesByCategoryJPQL = "select c.name, sum(oi.productPrice) from OrderItem oi " +
                " join oi.product p join p.categories c " +
                " where year(p.createdAt) = year(current_date) and month(p.createdAt) = month(current_date) " +
                " group by c.id";

        String totalSalesByClientJPQL = "select c.name, sum(oi.productPrice) from OrderItem oi " +
                " join oi.order o join o.client c " +
                " where year(o.createdAt) = year(current_date) and month(o.createdAt) >= (month(current_date) - 3) " +
                " group by c.id";

        TypedQuery<Object[]> totalSalesByMonthTypedQuery = entityManager.createQuery(totalSalesByMonthJPQL, Object[].class);
        TypedQuery<Object[]> totalSalesByCategoryTypedQuery = entityManager.createQuery(totalSalesByCategoryJPQL, Object[].class);
        TypedQuery<Object[]> totalSalesByClientTypedQuery = entityManager.createQuery(totalSalesByClientJPQL, Object[].class);

        List<Object[]> totalSalesByMonthList = totalSalesByMonthTypedQuery.getResultList();
        List<Object[]> totalSalesByCategoryList = totalSalesByCategoryTypedQuery.getResultList();
        List<Object[]> totalSalesByClientList = totalSalesByClientTypedQuery.getResultList();

        Assert.assertFalse(totalSalesByMonthList.isEmpty());
        Assert.assertFalse(totalSalesByCategoryList.isEmpty());
        Assert.assertFalse(totalSalesByClientList.isEmpty());

        totalSalesByMonthList.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
        totalSalesByCategoryList.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
        totalSalesByClientList.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void resultAggregation() {
        String amountOfProductsByCategoryJPQL = "select c.name, count(p.id) from Category c join c.products p group by c.id";

        String totalSalesByMonthJPQL = "select concat(year(o.createdAt), '/', function('monthname', o.createdAt)), sum(o.total) " +
                " from Order o " +
                " group by year(o.createdAt), month(o.createdAt) ";

        String totalSalesByCategoryJPQL = "select c.name, sum(oi.productPrice) from OrderItem oi " +
                " join oi.product p join p.categories c " +
                " group by c.id";

        String totalSalesByClientJPQL = "select c.name, sum(oi.productPrice) from OrderItem oi " +
                " join oi.order o join o.client c " +
                " group by c.id";

        String totalSalesByDayAndCategoryJPQL = "select " +
                " concat(year(o.createdAt), '/', month(o.createdAt), '/', day(o.createdAt)), " +
                " concat(c.name, ': ', sum(oi.productPrice)) " +
                " from OrderItem oi join oi.order o join oi.product pro join pro.categories c " +
                " group by year(o.createdAt), month(o.createdAt), day(o.createdAt), c.id " +
                " order by o.createdAt, c.name ";


        TypedQuery<Object[]> amountOfProductsByCategoryTypedQuery = entityManager.createQuery(amountOfProductsByCategoryJPQL, Object[].class);
        TypedQuery<Object[]> totalSalesByMonthTypedQuery = entityManager.createQuery(totalSalesByMonthJPQL, Object[].class);
        TypedQuery<Object[]> totalSalesByCategoryTypedQuery = entityManager.createQuery(totalSalesByCategoryJPQL, Object[].class);
        TypedQuery<Object[]> totalSalesByClientTypedQuery = entityManager.createQuery(totalSalesByClientJPQL, Object[].class);
        TypedQuery<Object[]> totalSalesByDayAndCategoryTypedQuery = entityManager.createQuery(totalSalesByDayAndCategoryJPQL, Object[].class);

        List<Object[]> amountOfProductsByCategoryList = amountOfProductsByCategoryTypedQuery.getResultList();
        List<Object[]> totalSalesByMonthList = totalSalesByMonthTypedQuery.getResultList();
        List<Object[]> totalSalesByCateogoryList = totalSalesByCategoryTypedQuery.getResultList();
        List<Object[]> totalSalesByClientList = totalSalesByClientTypedQuery.getResultList();
        List<Object[]> totalSalesByDayAndCategoryList = totalSalesByDayAndCategoryTypedQuery.getResultList();

        Assert.assertFalse(amountOfProductsByCategoryList.isEmpty());
        Assert.assertFalse(totalSalesByMonthList.isEmpty());
        Assert.assertFalse(totalSalesByCateogoryList.isEmpty());
        Assert.assertFalse(totalSalesByClientList.isEmpty());
        Assert.assertFalse(totalSalesByDayAndCategoryList.isEmpty());

        amountOfProductsByCategoryList.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
        totalSalesByMonthList.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
        totalSalesByCateogoryList.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
        totalSalesByClientList.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
        totalSalesByDayAndCategoryList.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }
}
