package com.studies.ecommerce.entitymanager;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CallbacksTest extends EntityManagerTest {

    @Test
    public void callbackTrigger() {

        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId());
        orderItem.setProductPrice(product.getPrice());
        orderItem.setAmount(1);
        orderItem.setProduct(product);

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setClient(client);
        order.setStatus(OrderStatus.WAITING);
        order.setTotal(BigDecimal.TEN);
        order.setItems(List.of(orderItem));

        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.flush();

        order.setStatus(OrderStatus.PAID);

        entityManager.getTransaction().commit();
        entityManager.clear();

        Order checkOrder = entityManager.find(Order.class, order.getId());

        Assert.assertNotNull(checkOrder.getCreatedAt());
        Assert.assertNotNull(checkOrder.getUpdatedAt());
    }

}
