package com.studies.ecommerce.criteria;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Client;
import com.studies.ecommerce.models.Client_;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ConditionalExpressionsCriteriaTest extends EntityManagerTest {

    @Test
    public void conditionalExpressionCriteriaLike() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.like(root.get(Client_.name), "%e%"));

        TypedQuery<Client> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Client> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

}
