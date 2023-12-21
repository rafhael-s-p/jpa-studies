package com.studies.ecommerce.criteria;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.Payment;
import com.studies.ecommerce.models.PaymentStatus;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class CriteriaJoinTest extends EntityManagerTest {

    @Test
    public void join() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        Join<Order, Payment> joinPayment = root.join("payment");

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder
                .equal(joinPayment.get("status"), PaymentStatus.PROCESSING));

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Order> list = typedQuery.getResultList();
        Assert.assertTrue(list.size() == 2);
    }

}
