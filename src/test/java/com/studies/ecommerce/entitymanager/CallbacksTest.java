package com.studies.ecommerce.entitymanager;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Client;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.OrderStatus;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CallbacksTest extends EntityManagerTest {

    @Test
    public void callbackTrigger() {
        Client client = entityManager.find(Client.class, 1);

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setClient(client);
        order.setStatus(OrderStatus.WAITING);
        order.setTotal(BigDecimal.TEN);

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
