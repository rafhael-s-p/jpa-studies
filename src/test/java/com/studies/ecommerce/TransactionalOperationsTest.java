package com.studies.ecommerce;

import com.studies.ecommerce.models.Product;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class TransactionalOperationsTest extends EntityManagerTest {

    @Test
    public void ObjectInsertion() {
        Product product = new Product();

        product.setId(2);
        product.setName("Canon Camera");
        product.setDescription("The best definition for your photos.");
        product.setPrice(new BigDecimal(5000));

        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product checkProduct = entityManager.find(Product.class, product.getId());

        Assert.assertNotNull(checkProduct);
    }

    @Test
    public void ObjectRemoval() {
        Product product = entityManager.find(Product.class, 3);

        entityManager.getTransaction().begin();
        entityManager.remove(product);
        entityManager.getTransaction().commit();

        Product checkProduct = entityManager.find(Product.class, 3);

        Assert.assertNull(checkProduct);
    }

}
