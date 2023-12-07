package com.studies.ecommerce.cascadeoperations;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.OrderItem;
import com.studies.ecommerce.models.OrderItemId;
import com.studies.ecommerce.models.Product;
import org.junit.Assert;
import org.junit.Test;

public class CascadeTypeRemoveTest extends EntityManagerTest {

    @Test
    public void removeProductCategoryRelation() {
        Product product = entityManager.find(Product.class, 1);

        Assert.assertFalse(product.getCategories().isEmpty());

        entityManager.getTransaction().begin();
        product.getCategories().clear();
        entityManager.getTransaction().commit();
        entityManager.clear();

        Product checkProduct = entityManager.find(Product.class, product.getId());

        Assert.assertTrue(checkProduct.getCategories().isEmpty());
    }

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
