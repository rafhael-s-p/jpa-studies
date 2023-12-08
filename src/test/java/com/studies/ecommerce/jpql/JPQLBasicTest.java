package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.dto.ProductDTO;
import com.studies.ecommerce.models.Client;
import com.studies.ecommerce.models.Order;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class JPQLBasicTest extends EntityManagerTest {

    @Test
    public void findById() {
        TypedQuery<Order> typedQuery = entityManager
                .createQuery("select o from Order o where o.id = 1", Order.class);
        Order order = typedQuery.getSingleResult();
        Assert.assertNotNull(order);
    }

    @Test
    public void typedQueryVsQuery() {
        String jpql = "select o from Order o where o.id = 1";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        Order order1 = typedQuery.getSingleResult();
        Assert.assertNotNull(order1);

        Query query = entityManager.createQuery(jpql);
        Order order2 = (Order) query.getSingleResult();
        Assert.assertNotNull(order2);
    }

    @Test
    public void attributeReturn() {
        String jpql = "select p.name from Product p";

        TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);
        List<String> list = typedQuery.getResultList();
        Assert.assertEquals(String.class, list.get(0).getClass());

        String clientJPQL = "select o.client from Order o";
        TypedQuery<Client> typedQueryClient = entityManager.createQuery(clientJPQL, Client.class);
        List<Client> clientsList = typedQueryClient.getResultList();
        Assert.assertEquals(Client.class, clientsList.get(0).getClass());
    }

    @Test
    public void resultProjection() {
        String jpql = "select id, name from Product";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> list = typedQuery.getResultList();

        Assert.assertEquals(2, list.get(0).length);

        list.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void dtoProjection() {
        String jpql = "select new com.studies.ecommerce.dto.ProductDTO(id, name) from Product";

        TypedQuery<ProductDTO> typedQuery = entityManager.createQuery(jpql, ProductDTO.class);
        List<ProductDTO> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(p -> System.out.println(p.getId() + ", " + p.getName()));
    }

}
