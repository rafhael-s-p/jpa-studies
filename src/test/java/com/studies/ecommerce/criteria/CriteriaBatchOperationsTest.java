package com.studies.ecommerce.criteria;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Category;
import com.studies.ecommerce.models.Category_;
import com.studies.ecommerce.models.Product;
import com.studies.ecommerce.models.Product_;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.math.BigDecimal;

public class CriteriaBatchOperationsTest extends EntityManagerTest {

    public static final Integer BOOKS_CATEGORY = 2;
    public static final Integer DELETE_ID_START = 5;
    public static final Integer DELETE_ID_END = 12;

    @Test
    public void criteriaBatchDelete() {
        entityManager.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Product> criteriaDelete = criteriaBuilder.createCriteriaDelete(Product.class);
        Root<Product> root = criteriaDelete.from(Product.class);

        criteriaDelete.where(criteriaBuilder.between(root.get(Product_.id), DELETE_ID_START, DELETE_ID_END));

        Query query = entityManager.createQuery(criteriaDelete);
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }

    @Test
    public void criteriaBatchUpdate() {
        entityManager.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Product> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Product.class);
        Root<Product> root = criteriaUpdate.from(Product.class);

        criteriaUpdate.set(root.get(Product_.price), criteriaBuilder.prod(root.get(Product_.price), new BigDecimal("1.1")));

        Subquery<Integer> subquery = criteriaUpdate.subquery(Integer.class);
        Root<Product> subqueryRoot = subquery.correlate(root);
        Join<Product, Category> joinCategory = subqueryRoot.join(Product_.categories);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(criteriaBuilder.equal(joinCategory.get(Category_.id), BOOKS_CATEGORY));

        criteriaUpdate.where(criteriaBuilder.exists(subquery));

        Query query = entityManager.createQuery(criteriaUpdate);
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }

}