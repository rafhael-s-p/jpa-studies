package com.studies.ecommerce.relationship;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Category;
import org.junit.Assert;
import org.junit.Test;

public class AutoRelationshipTest extends EntityManagerTest {

    @Test
    public void checkCategoryAutoRelationship() {
        Category categoryFather = new Category();
        categoryFather.setName("Electronics");

        Category category = new Category();
        category.setName("Phones");
        category.setCategoryFather(categoryFather);


        entityManager.getTransaction().begin();
        entityManager.persist(categoryFather);
        entityManager.persist(category);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Category checkCategory = entityManager.find(Category.class, category.getId());
        Assert.assertNotNull(checkCategory.getCategoryFather());

        Category checkCategoryFather = entityManager.find(Category.class, categoryFather.getId());
        Assert.assertFalse(checkCategoryFather.getCategories().isEmpty());
    }
}
