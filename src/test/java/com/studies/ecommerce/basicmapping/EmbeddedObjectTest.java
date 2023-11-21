package com.studies.ecommerce.basicmapping;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Address;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.OrderStatus;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EmbeddedObjectTest extends EntityManagerTest {

    @Test
    public void builtInObjectMapping() {

        Address deliveryAddress = new Address();
        deliveryAddress.setPostal("11111-111");
        deliveryAddress.setStreet("First Avenue");
        deliveryAddress.setComplement("Apt 101");
        deliveryAddress.setNumber("100");
        deliveryAddress.setDistrict("Downtown");
        deliveryAddress.setCity("San Francisco");
        deliveryAddress.setState("California");

        Order order = new Order();

        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.WAITING);
        order.setTotal(new BigDecimal(250));
        order.setDeliveryAddress(deliveryAddress);

        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order checkOrder = entityManager.find(Order.class, order.getId());

        Assert.assertNotNull(checkOrder);
        Assert.assertNotNull(checkOrder.getDeliveryAddress());
        Assert.assertNotNull(checkOrder.getDeliveryAddress().getPostal());
    }
}
