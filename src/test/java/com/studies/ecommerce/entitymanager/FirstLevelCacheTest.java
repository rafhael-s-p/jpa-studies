package com.studies.ecommerce.entitymanager;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Product;
import org.junit.Test;

public class FirstLevelCacheTest extends EntityManagerTest {

    @Test
    public void checkCache() {
        Product product = entityManager.find(Product.class, 1);
        System.out.println(product.getName());

        System.out.println("=========> In the second call JPA will find the product in the memory <=========");

        // In order to force JPA search in the database is necessary call entityManager.clear or
        // entityManager.close();
        // entityManager = entityManagerFactory.createEntityManager();

        Product searchedProduct = entityManager.find(Product.class, product.getId());
        System.out.println(searchedProduct.getName());
    }
}
