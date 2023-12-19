package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.Product;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class NamedQueryTest extends EntityManagerTest {

    @Test
    public void productXmlNamedQuery() {
        TypedQuery<Product> typedQuery = entityManager
                .createNamedQuery("Product.all", Product.class);

        List<Product> list = typedQuery.getResultList();

        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void orderXmlNamedQuery() {
        TypedQuery<Order> typedQuery = entityManager
                .createNamedQuery("Order.all", Order.class);

        List<Order> list = typedQuery.getResultList();

        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void xmlNamedQuery() {
        TypedQuery<Order> typedQuery = entityManager
                .createNamedQuery("Order.list", Order.class);

        List<Order> list = typedQuery.getResultList();

        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void namedQuery() {
        TypedQuery<Product> typedQuery = entityManager
                .createNamedQuery("Product.listByCategory", Product.class);
        typedQuery.setParameter("category", 2);

        List<Product> list = typedQuery.getResultList();

        Assert.assertFalse(list.isEmpty());
    }

}
