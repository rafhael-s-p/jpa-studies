package com.studies.ecommerce.advancedmapping;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Client;
import com.studies.ecommerce.models.Product;
import com.studies.ecommerce.models.ProductCharacteristic;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

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

    @Test
    public void applyContact() {
        entityManager.getTransaction().begin();

        Client client = entityManager.find(Client.class, 1);
        client.setContacts(Collections.singletonMap("email", "name@domain.com"));

        entityManager.getTransaction().commit();

        entityManager.clear();

        Client checkClient = entityManager.find(Client.class, client.getId());

        Assert.assertEquals("name@domain.com", checkClient.getContacts().get("email"));
    }

}
