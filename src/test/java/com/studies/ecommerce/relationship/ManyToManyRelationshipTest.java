package com.studies.ecommerce.relationship;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Category;
import com.studies.ecommerce.models.Product;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

public class ManyToManyRelationshipTest extends EntityManagerTest {

    @Test
    public void checkManyToManyRelationship() {
        Product product = entityManager.find(Product.class, 1);
        Category category = entityManager.find(Category.class, 1);

        entityManager.getTransaction().begin();
        // category.setProducts(Arrays.asList(product)); // it does not work because product is the relationship owner
        product.setCategories(Arrays.asList(category));
        product.setUpdatedAt(LocalDateTime.now());
        entityManager.getTransaction().commit();
        entityManager.clear();

        Category checkCategory = entityManager.find(Category.class, category.getId());

        Assert.assertFalse(checkCategory.getProducts().isEmpty());
    }

}
