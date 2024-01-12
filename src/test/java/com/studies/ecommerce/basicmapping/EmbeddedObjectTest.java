package com.studies.ecommerce.basicmapping;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class EmbeddedObjectTest extends EntityManagerTest {

    @Test
    public void builtInObjectMapping() {

        Product product = entityManager.find(Product.class, 1);
        Client client = entityManager.find(Client.class, 1);

        Address deliveryAddress = new Address();
        deliveryAddress.setPostal("11111-111");
        deliveryAddress.setStreet("First Avenue");
        deliveryAddress.setComplement("Apt 101");
        deliveryAddress.setNumber("100");
        deliveryAddress.setDistrict("Downtown");
        deliveryAddress.setCity("San Francisco");
        deliveryAddress.setState("California");

        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId());
        orderItem.setProductPrice(product.getPrice());
        orderItem.setAmount(1);
        orderItem.setProduct(product);

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setItems(List.of(orderItem));
        order.setStatus(OrderStatus.WAITING);
        order.setTotal(new BigDecimal(250));
        order.setDeliveryAddress(deliveryAddress);
        order.setClient(client);

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
