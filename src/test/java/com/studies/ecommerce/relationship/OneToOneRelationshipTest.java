package com.studies.ecommerce.relationship;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Invoice;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.PaymentCard;
import com.studies.ecommerce.models.PaymentStatus;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class OneToOneRelationshipTest extends EntityManagerTest {

    @Test
    public void checkOneToOneCardPaymentRelationship() {
        Order order = entityManager.find(Order.class, 5);

        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardNumber("5123716344050150");
        paymentCard.setStatus(PaymentStatus.PROCESSING);
        paymentCard.setOrder(order);

        entityManager.getTransaction().begin();
        entityManager.persist(paymentCard);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Order checkOrder = entityManager.find(Order.class, order.getId());

        Assert.assertNotNull(checkOrder.getPayment());
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
