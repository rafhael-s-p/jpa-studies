package com.studies.ecommerce.criteria;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.OrderStatus;
import com.studies.ecommerce.models.Order_;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

public class LogicalOperatorsCriteriaTest extends EntityManagerTest {

    @Test
    public void logicalOperators() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root);

        criteriaQuery.where(
                criteriaBuilder.or(
                        criteriaBuilder.equal(
                                root.get(Order_.status), OrderStatus.WAITING),
                        criteriaBuilder.equal(
                                root.get(Order_.status), OrderStatus.PAID)
                ),
                criteriaBuilder.greaterThan(
                        root.get(Order_.total), new BigDecimal(499))
        );

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(p -> System.out.println("ID: " + p.getId() + ", Total: " + p.getTotal()));
    }

}