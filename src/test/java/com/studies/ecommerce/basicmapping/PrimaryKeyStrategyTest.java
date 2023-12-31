package com.studies.ecommerce.basicmapping;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Category;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class PrimaryKeyStrategyTest extends EntityManagerTest {

    @Ignore
    @Test
    public void autoStrategy() {
        Category category = new Category();
        category.setName("Electronics");

        entityManager.getTransaction().begin();
        entityManager.persist(category);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Category checkCategory = entityManager.find(Category.class, category.getId());

        Assert.assertNotNull(checkCategory);
    }

    @Ignore
    @Test
    public void sequenceGeneratorStrategy() {
        Category category = new Category();
        category.setName("Electronics");

        entityManager.getTransaction().begin();
        entityManager.persist(category);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Category checkCategory = entityManager.find(Category.class, category.getId());

        Assert.assertNotNull(checkCategory);
    }

    @Ignore
    @Test
    public void tableGeneratorStrategy() {
        Category category = new Category();
        category.setName("Electronics");

        entityManager.getTransaction().begin();
        entityManager.persist(category);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Category checkCategory = entityManager.find(Category.class, category.getId());

        Assert.assertNotNull(checkCategory);
    }

    @Test
    public void identityGeneratorStrategy() {
        Category category = new Category();
        category.setName("Winter Sports");

        entityManager.getTransaction().begin();
        entityManager.persist(category);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Category checkCategory = entityManager.find(Category.class, category.getId());

        Assert.assertNotNull(checkCategory);
    }

}
