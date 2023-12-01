package com.studies.ecommerce.advancedmapping;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Product;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ColumnDetailsTest extends EntityManagerTest {

    @Test
    public void preventColumnInsertionUpdatedAt() {

        Product product = new Product();
        product.setName("Smartphone keyboard");
        product.setDescription("The most comfortable");
        product.setPrice(BigDecimal.ONE);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product checkProduct = entityManager.find(Product.class, product.getId());

        Assert.assertNotNull(checkProduct.getCreatedAt());
        Assert.assertNull(checkProduct.getUpdatedAt());
    }

    @Test
    public void preventColumnInsertionCreatedAt() {
        entityManager.getTransaction().begin();

        Product product = entityManager.find(Product.class, 1);
        product.setPrice(BigDecimal.TEN);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        entityManager.getTransaction().commit();

        entityManager.clear();

        Product checkProduct = entityManager.find(Product.class, product.getId());

        Assert.assertNotEquals(product.getCreatedAt().truncatedTo(ChronoUnit.SECONDS),
                checkProduct.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));
        Assert.assertEquals(product.getUpdatedAt().truncatedTo(ChronoUnit.SECONDS),
                checkProduct.getUpdatedAt().truncatedTo(ChronoUnit.SECONDS));
    }
}
