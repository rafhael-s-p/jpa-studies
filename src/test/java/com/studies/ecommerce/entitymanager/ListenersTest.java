package com.studies.ecommerce.entitymanager;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Client;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.OrderStatus;
import com.studies.ecommerce.models.Product;
import org.junit.Assert;
import org.junit.Test;

public class ListenersTest extends EntityManagerTest {

    @Test
    public void entityLoading() {
        Product product = entityManager.find(Product.class, 1);
        Order order = entityManager.find(Order.class, 1);
    }

    @Test
    public void callbackTrigger() {
        Client client = entityManager.find(Client.class, 1);

        Order order = new Order();

        order.setClient(client);
        order.setStatus(OrderStatus.WAITING);

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
