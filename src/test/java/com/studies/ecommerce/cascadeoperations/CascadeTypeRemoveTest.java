package com.studies.ecommerce.cascadeoperations;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.OrderItem;
import com.studies.ecommerce.models.OrderItemId;
import org.junit.Assert;

public class CascadeTypeRemoveTest extends EntityManagerTest {

    // @Test
    public void removeOrderAndItems() {
        Order order = entityManager.find(Order.class, 1);

        entityManager.getTransaction().begin();
        entityManager.remove(order); // it must assign CascadeType.MERGE on items attribute
        entityManager.getTransaction().commit();
        entityManager.clear();

        Order checkOrder = entityManager.find(Order.class, order.getId());

        Assert.assertNull(checkOrder);

    }

    // @Test
    public void removeOrderItemAndOrder() {
        OrderItem orderItem = entityManager.find(
                OrderItem.class, new OrderItemId(1, 1));

        entityManager.getTransaction().begin();
        entityManager.remove(orderItem); // it must assign CascadeType.MERGE on order attribute
        entityManager.getTransaction().commit();
        entityManager.clear();

        Order checkOrder = entityManager.find(Order.class, orderItem.getOrder().getId());

        Assert.assertNull(checkOrder);
    }

}
