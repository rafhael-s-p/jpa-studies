package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Invoice;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.PaymentStatus;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class PassingSQLParametersTest extends EntityManagerTest {

    @Test
    public void passingDateParameter() {
        String jpql = "select inv from Invoice inv where inv.emissionDate <= ?1";

        TypedQuery<Invoice> typedQuery = entityManager.createQuery(jpql, Invoice.class);
        typedQuery.setParameter(1, new Date(), TemporalType.TIMESTAMP);

        List<Invoice> list = typedQuery.getResultList();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void passingParameter() {
        String jpql = "select o from Order o join o.payment p " +
                "where o.id = :orderId and p.status = ?1";

        TypedQuery<Order> typedQuery = entityManager.createQuery(jpql, Order.class);
        typedQuery.setParameter("orderId", 2);
        typedQuery.setParameter(1, PaymentStatus.PROCESSING);

        List<Order> list = typedQuery.getResultList();
        Assert.assertEquals(1, list.size());
    }
}
