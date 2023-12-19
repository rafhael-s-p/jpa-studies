package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Category;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class JPQLPaginationTest extends EntityManagerTest {

    @Test
    public void resultPagination() {
        String jpql = "select c from Category c order by c.name";

        TypedQuery<Category> typedQuery = entityManager.createQuery(jpql, Category.class);

        // FIRST_RESULT = MAX_RESULTS * (page - 1)
        typedQuery.setFirstResult(6);
        typedQuery.setMaxResults(2);

        List<Category> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(c -> System.out.println(c.getId() + ", " + c.getName()));
    }

}
