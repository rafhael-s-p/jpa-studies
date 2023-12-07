package com.studies.ecommerce.cascadeoperations;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.*;
import org.junit.Assert;

import java.util.Arrays;

public class CascadeTypeMergeTest extends EntityManagerTest {

    // @Test
    public void updateOrderTogetherItems() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Order order = new Order();
        order.setId(1);
        order.setClient(client);
        order.setStatus(OrderStatus.WAITING);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId());
        orderItem.getId().setOrderId(order.getId());
        orderItem.getId().setProductId(product.getId());
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setAmount(3);
        orderItem.setProductPrice(product.getPrice());
        order.setItems(Arrays.asList(orderItem)); // it must assign CascadeType.MERGE on items attribute

        entityManager.getTransaction().begin();
        entityManager.merge(order);
        entityManager.getTransaction().commit();
        entityManager.clear();

        OrderItem checkOrderItem = entityManager.find(OrderItem.class, orderItem.getId());

        Assert.assertTrue(checkOrderItem.getAmount().equals(3));
    }

    // @Test
    public void updateOrderItemTogetherOrder() {
        Client client = entityManager.find(Client.class, 1);
        Product product = entityManager.find(Product.class, 1);

        Order order = new Order();
        order.setId(1);
        order.setClient(client);
        order.setStatus(OrderStatus.PAID);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(new OrderItemId());
        orderItem.getId().setOrderId(order.getId());
        orderItem.getId().setProductId(product.getId());
        orderItem.setOrder(order); // it must assign CascadeType.MERGE on order attribute
        orderItem.setProduct(product);
        orderItem.setAmount(5);
        orderItem.setProductPrice(product.getPrice());
        order.setItems(Arrays.asList(orderItem));

        entityManager.getTransaction().begin();
        entityManager.merge(orderItem);
        entityManager.getTransaction().commit();
        entityManager.clear();

        OrderItem checkOrderItem = entityManager.find(OrderItem.class, orderItem.getId());

        Assert.assertTrue(OrderStatus.PAID.equals(checkOrderItem.getOrder().getStatus()));
    }

}
