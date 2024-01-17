package com.studies.ecommerce.jpastarting;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Product;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductOperationsTest extends EntityManagerTest {

    @Test
    public void objectInsert() {
        Product product = new Product();

        product.setName("Canon Camera");
        product.setDescription("The best definition for your photos.");
        product.setPrice(new BigDecimal(5000));
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Product checkProduct = entityManager.find(Product.class, product.getId());

        Assert.assertNotNull(checkProduct);
    }

    @Test
    public void objectInsertMerge() {
        Product product = new Product();

        product.setName("Rode Videomic Microphone");
        product.setDescription("The best sound quality.");
        product.setPrice(new BigDecimal(1000));
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        entityManager.getTransaction().begin();
        Product savedProduct = entityManager.merge(product);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Product checkProduct = entityManager.find(Product.class, savedProduct.getId());

        Assert.assertNotNull(checkProduct);
    }

    @Test
    public void objectUpdate() {
        Product product = new Product();

        product.setId(1);
        product.setName("Kindle Paperwhite");
        product.setDescription("Meet the new Kindle.");
        product.setPrice(new BigDecimal(599));
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.merge(product);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Product checkProduct = entityManager.find(Product.class, product.getId());

        Assert.assertNotNull(checkProduct);
        Assert.assertEquals("Kindle Paperwhite", checkProduct.getName());
    }

    @Test
    public void managedObjectUpdate() {
        Product product = entityManager.find(Product.class, 1);

        entityManager.getTransaction().begin();
        product.setName("Kindle Paperwhite 2nd Generation");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product checkProduct = entityManager.find(Product.class, product.getId());

        Assert.assertEquals("Kindle Paperwhite 2nd Generation", checkProduct.getName());
    }

    @Ignore
    @Test
    public void objectDelete() {
        Product product = entityManager.find(Product.class, 3);

        entityManager.getTransaction().begin();
        entityManager.remove(product);
        entityManager.getTransaction().commit();

        Product checkProduct = entityManager.find(Product.class, 3);

        Assert.assertNull(checkProduct);
    }

    @Test
    public void differenceBetweenPersistAndMerge() {
        // PERSIST
        Product productPersist = new Product();

        productPersist.setName("One Plus Smartphone");
        productPersist.setDescription("The fastest processor.");
        productPersist.setPrice(new BigDecimal(2000));
        productPersist.setCreatedAt(LocalDateTime.now());
        productPersist.setUpdatedAt(LocalDateTime.now());

        entityManager.getTransaction().begin();
        entityManager.persist(productPersist);
        productPersist.setName("One Plus Deluxe Smartphone");
        entityManager.getTransaction().commit();
        entityManager.clear();

        Product checkProductPersist = entityManager.find(Product.class, productPersist.getId());

        Assert.assertNotNull(checkProductPersist);

        // MERGE
        Product productMerge = new Product();
        productMerge.setName("Dell Laptop");
        productMerge.setDescription("The best in the category.");
        productMerge.setPrice(new BigDecimal(2000));
        productMerge.setCreatedAt(LocalDateTime.now());
        productMerge.setUpdatedAt(LocalDateTime.now());

        entityManager.getTransaction().begin();
        productMerge = entityManager.merge(productMerge);
        productMerge.setName("Dell Laptop Deluxe");
        entityManager.getTransaction().commit();
        entityManager.clear();

        Product checkProductMerge = entityManager.find(Product.class, productMerge.getId());

        Assert.assertNotNull(checkProductMerge);
    }

    @Test
    public void preventDatabaseOperation() {
        Product product = entityManager.find(Product.class, 1);
        entityManager.detach(product);

        entityManager.getTransaction().begin();
        product.setName("Kindle Paperwhite 2nd Generation");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product checkProduct = entityManager.find(Product.class, product.getId());

        Assert.assertEquals("Kindle", checkProduct.getName());
    }

}
