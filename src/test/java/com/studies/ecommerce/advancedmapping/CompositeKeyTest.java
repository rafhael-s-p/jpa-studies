package com.studies.ecommerce.advancedmapping;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class CompositeKeyTest extends EntityManagerTest {

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

        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId());
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setProductPrice(product.getPrice());
        orderItem.setAmount(1);
        order.setItems(List.of(orderItem));

        entityManager.persist(order);
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
