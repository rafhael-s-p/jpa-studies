package com.studies.ecommerce.multitenancy;

import com.studies.ecommerce.EntityManagerFactoryTest;
import com.studies.ecommerce.hibernate.EcmCurrentTenantIdentifierResolver;
import com.studies.ecommerce.models.Product;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;

public class MultiTenancyTest extends EntityManagerFactoryTest {

    @Test
    public void machineApproach() {

        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("host1_ecommerce");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        Product product1 = entityManager1.find(Product.class, 1);
        Assert.assertEquals("Kindle", product1.getName());
        entityManager1.close();

        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("host2_ecommerce");
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        Product product2 = entityManager2.find(Product.class, 1);
        Assert.assertEquals("Kindle", product2.getName());
        entityManager2.close();
    }

    @Ignore
    @Test
    public void schemaApproach() {

        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("db1_ecommerce");
        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        Product product1 = entityManager1.find(Product.class, 1);
        Assert.assertEquals("Kindle", product1.getName());
        entityManager1.close();

        EcmCurrentTenantIdentifierResolver.setTenantIdentifier("db2_ecommerce");
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        Product product2 = entityManager2.find(Product.class, 1);
        Assert.assertEquals("Kindle", product2.getName());
        entityManager2.close();
    }

}
