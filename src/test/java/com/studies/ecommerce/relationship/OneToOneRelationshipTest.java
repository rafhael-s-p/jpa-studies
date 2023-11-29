package com.studies.ecommerce.relationship;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.CardPayment;
import com.studies.ecommerce.models.Invoice;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.PaymentStatus;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class OneToOneRelationshipTest extends EntityManagerTest {

    @Test
    public void checkOneToOneCardPaymentRelationship() {
        Order order = entityManager.find(Order.class, 1);

        CardPayment cardPayment = new CardPayment();
        cardPayment.setNumber("5123716344050150");
        cardPayment.setStatus(PaymentStatus.PROCESSING);
        cardPayment.setOrder(order);

        entityManager.getTransaction().begin();
        entityManager.persist(cardPayment);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order checkOrder = entityManager.find(Order.class, order.getId());

        Assert.assertNotNull(checkOrder.getCardPayment());
    }

    @Test
    public void checkOneToOneInvoiceRelationship() {
        Order order = entityManager.find(Order.class, 1);

        Invoice invoice = new Invoice();
        invoice.setXml("<xml></xml>".getBytes());
        invoice.setEmissionDate(new Date());
        invoice.setOrder(order);

        entityManager.getTransaction().begin();
        entityManager.persist(invoice);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Order checkOrder = entityManager.find(Order.class, order.getId());

        Assert.assertNotNull(checkOrder.getInvoice());
    }
}
