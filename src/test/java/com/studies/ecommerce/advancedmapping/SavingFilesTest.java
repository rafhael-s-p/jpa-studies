package com.studies.ecommerce.advancedmapping;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Invoice;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.Product;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

public class SavingFilesTest extends EntityManagerTest {

    @Test
    public void saveInvoiceXml() {
        Order order = entityManager.find(Order.class, 1);

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setEmissionDate(new Date());
        invoice.setXml(loadInvoice());

        entityManager.getTransaction().begin();
        entityManager.persist(invoice);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Invoice checkInvoice = entityManager.find(Invoice.class, invoice.getId());

        Assert.assertNotNull(checkInvoice.getXml());
        Assert.assertTrue(checkInvoice.getXml().length > 0);

    }

    @Test
    public void saveProductPhoto(){
        entityManager.getTransaction().begin();
        Product product = entityManager.find(Product.class, 1);
        product.setProductPhoto(loadProductPhoto());
        product.setUpdatedAt(LocalDateTime.now());
        entityManager.getTransaction().commit();

        entityManager.clear();

        Product checkProduct = entityManager.find(Product.class, 1);

        Assert.assertNotNull(checkProduct.getProductPhoto());
        Assert.assertTrue(checkProduct.getProductPhoto().length > 0);
    }

    private static byte[] loadProductPhoto() {
        return loadFile("/kindle.jpg");
    }

    private static byte[] loadInvoice() {
        return loadFile("/invoice.xml");
    }

    private static byte[] loadFile(String fileName) {
        try {
            return SavingFilesTest.class.getResourceAsStream(fileName).readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
