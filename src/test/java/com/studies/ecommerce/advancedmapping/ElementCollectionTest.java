package com.studies.ecommerce.advancedmapping;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Product;
import com.studies.ecommerce.models.ProductCharacteristic;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ElementCollectionTest extends EntityManagerTest {

    @Test
    public void applyTags() {
        entityManager.getTransaction().begin();

        Product product = entityManager.find(Product.class, 1);
        product.setTags(Arrays.asList("ebook", "digital-book"));

        entityManager.getTransaction().commit();

        entityManager.clear();

        Product checkProduct = entityManager.find(Product.class, product.getId());

        Assert.assertFalse(checkProduct.getTags().isEmpty());
    }

    @Test
    public void applyCharacteristics() {
        entityManager.getTransaction().begin();

        Product product = entityManager.find(Product.class, 1);
        product.setCharacteristics(Arrays.asList(new ProductCharacteristic("screen", "220x500"),
                new ProductCharacteristic("color", "black")));

        entityManager.getTransaction().commit();

        entityManager.clear();

        Product checkProduct = entityManager.find(Product.class, product.getId());

        Assert.assertFalse(checkProduct.getCharacteristics().isEmpty());
    }

}
