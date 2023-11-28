package com.studies.ecommerce.advancedmapping;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class CompoundKeyTest extends EntityManagerTest {

    @Test
    public void saveItem() {
        entityManager.getTransaction().begin();

        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Order order = new Order();
        order.setClient(client);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.WAITING);
        order.setTotal(product.getPrice());

        entityManager.persist(order);

        entityManager.flush();

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setProductPrice(product.getPrice());
        orderItem.setAmount(1);

        entityManager.persist(orderItem);

        entityManager.getTransaction().commit();

        entityManager.clear();

        Order checkOrder = entityManager.find(Order.class, order.getId());

        Assert.assertNotNull(checkOrder);
        Assert.assertFalse(checkOrder.getItems().isEmpty());

    }

    @Test
    public void findItem() {
        OrderItem orderItem = entityManager.find(
                OrderItem.class, new OrderItemId(1, 1));

        Assert.assertNotNull(orderItem);
    }
}
