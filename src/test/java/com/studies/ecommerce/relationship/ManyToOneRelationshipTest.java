package com.studies.ecommerce.relationship;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ManyToOneRelationshipTest extends EntityManagerTest {

    @Test
    public void checkManyToOneOrderRelationship() {
        Client client = entityManager.find(Client.class, 1);

        Order order = new Order();
        order.setStatus(OrderStatus.WAITING);
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(BigDecimal.TEN);

        order.setClient(client);

        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order checkOrder = entityManager.find(Order.class, order.getId());

        Assert.assertNotNull(checkOrder.getClient());
    }

    @Test
    public void checkManyToOneOrderItemRelationship() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Order order = new Order();
        order.setStatus(OrderStatus.WAITING);
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(BigDecimal.TEN);
        order.setClient(client);

        OrderItem orderItem = new OrderItem();
        orderItem.setProductPrice(product.getPrice());
        orderItem.setAmount(1);
        orderItem.setOrder(order);
        orderItem.setProduct(product);

        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.persist(orderItem);
        entityManager.getTransaction().commit();

        entityManager.clear();

        OrderItem checkOrderItem = entityManager.find(OrderItem.class, orderItem.getId());

        Assert.assertNotNull(checkOrderItem.getOrder());
        Assert.assertNotNull(checkOrderItem.getProduct());
    }
}