package com.studies.ecommerce;

import com.studies.ecommerce.models.Product;
import org.junit.Assert;
import org.junit.Test;

public class FindingRegistersTest extends EntityManagerTest {

    @Test
    public void findById() {
        Product product = entityManager.find(Product.class, 1);

        // getReference only perform a query if some property on Assert is used
        // Product product = entityManager.getReference(Product.class, 1);

        Assert.assertNotNull(product);
        Assert.assertEquals("Kindle", product.getName());
    }

    @Test
    public void updateReference() {
        Product product = entityManager.find(Product.class, 1);
        product.setName("Updated Name");

        // Perform entity on database again even after setting some property
        entityManager.refresh(product);

        Assert.assertEquals("Kindle", product.getName());
    }

}
