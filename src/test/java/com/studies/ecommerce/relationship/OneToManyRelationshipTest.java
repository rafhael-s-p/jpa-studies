package com.studies.ecommerce.relationship;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OneToManyRelationshipTest extends EntityManagerTest {

    @Test
    public void checkOneToManyClientRelationship() {

        Product product = entityManager.find(Product.class, 1);
        Client client = entityManager.find(Client.class, 1);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId());
        orderItem.setProductPrice(product.getPrice());
        orderItem.setAmount(1);
        orderItem.setProduct(product);

        Order order = new Order();
        order.setStatus(OrderStatus.WAITING);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotal(BigDecimal.TEN);
        order.setItems(List.of(orderItem));
        order.setClient(client);

        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client checkClient = entityManager.find(Client.class, client.getId());

        Assert.assertFalse(checkClient.getOrders().isEmpty());
    }

    @Test
    public void checkOneToManyOrderRelationship() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Order order = new Order();
        order.setStatus(OrderStatus.WAITING);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotal(BigDecimal.TEN);
        order.setClient(client);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId());
        orderItem.setProductPrice(product.getPrice());
        orderItem.setAmount(1);
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        order.setItems(List.of(orderItem));

        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.persist(orderItem);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Order checkOrder = entityManager.find(Order.class, order.getId());

        Assert.assertFalse(checkOrder.getItems().isEmpty());
    }

}
