package com.studies.ecommerce.cascadeoperations;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class CascadeTypePersistTest extends EntityManagerTest {

    @Test
    public void persistOrderItemTogetherOrder() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setClient(client);
        order.setTotal(product.getPrice());
        order.setStatus(OrderStatus.WAITING);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId());
        orderItem.setOrder(order);// It is not necessary to assign CascadeType.PERSIST because it has @MapsId.
        orderItem.setProduct(product);
        orderItem.setAmount(1);
        orderItem.setProductPrice(product.getPrice());

        entityManager.getTransaction().begin();
        entityManager.persist(orderItem);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Order checkOrder = entityManager.find(Order.class, order.getId());

        Assert.assertNotNull(checkOrder);

    }

    // @Test
    public void persistOrderTogetherItems() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setClient(client);
        order.setTotal(product.getPrice());
        order.setStatus(OrderStatus.WAITING);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId());
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setAmount(1);
        orderItem.setProductPrice(product.getPrice());
        order.setItems(Arrays.asList(orderItem)); // it must assign CascadeType.PERSIST in order items attribute

        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Order checkOrder = entityManager.find(Order.class, order.getId());

        Assert.assertNotNull(checkOrder);
        Assert.assertFalse(checkOrder.getItems().isEmpty());

    }

    // @Test
    public void persistOrderTogetherClient() {
        Client client = new Client();
        client.setBirthday(LocalDate.of(1942, 11, 20));
        client.setGender(Gender.MALE);
        client.setName("Joe Biden");
        client.setSsn("303144873");

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setClient(client); // it must assign CascadeType.PERSIST in client attribute
        order.setTotal(BigDecimal.ZERO);
        order.setStatus(OrderStatus.WAITING);

        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Client checkClient = entityManager.find(Client.class, client.getId());

        Assert.assertNotNull(checkClient);
    }

}
