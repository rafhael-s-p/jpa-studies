package com.studies.ecommerce.relationship;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Order;
import org.junit.Assert;
import org.junit.Test;

public class RemovingReferencedEntitiesTest extends EntityManagerTest {

    @Test
    public void removeRelatedEntity() {
        Order order = entityManager.find(Order.class, 1);

        Assert.assertFalse(order.getItems().isEmpty());

        entityManager.getTransaction().begin();
        order.getItems().forEach(i -> entityManager.remove(i));
        entityManager.remove(order);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Order checkOrder = entityManager.find(Order.class, 1);

        Assert.assertNull(checkOrder);
    }

}
