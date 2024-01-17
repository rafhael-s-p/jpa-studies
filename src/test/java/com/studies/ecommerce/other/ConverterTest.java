package com.studies.ecommerce.other;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Product;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class ConverterTest extends EntityManagerTest {

    @Test
    public void converter() {
        Product product = new Product();
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setName("Dell Notebook Charger");
        product.setActive(Boolean.TRUE);

        entityManager.getTransaction().begin();

        entityManager.persist(product);

        entityManager.getTransaction().commit();

        entityManager.clear();

        Product checkProduct = entityManager.find(Product.class, product.getId());
        Assert.assertTrue(checkProduct.getActive());
    }

}
