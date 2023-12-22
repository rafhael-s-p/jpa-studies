package com.studies.ecommerce.criteria;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Product;
import com.studies.ecommerce.models.Product_;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class MetaModelTest extends EntityManagerTest {

    @Test
    public void jpaMetaModel() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.or(
                criteriaBuilder.like(root.get(Product_.name), "%C%"),
                criteriaBuilder.like(root.get(Product_.description), "%C%")
        ));

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

}
