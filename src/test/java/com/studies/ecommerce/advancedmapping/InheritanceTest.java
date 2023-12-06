package com.studies.ecommerce.advancedmapping;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class InheritanceTest extends EntityManagerTest {

    @Test
    public void saveClient() {
        Client client = new Client();
        client.setName("Mary Jane");
        client.setGender(Gender.FEMALE);
        client.setSsn("426042848");

        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Client checkClient = entityManager.find(Client.class, client.getId());

        Assert.assertNotNull(checkClient.getId());
    }

    @Test
    public void findPayments() {
        List<Payment> payments = entityManager
                .createQuery("select p from Payment p")
                .getResultList();

        Assert.assertFalse(payments.isEmpty());
    }

    @Test
    public void insertPaymentOrder() {
        Order order = entityManager.find(Order.class, 1);

        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setOrder(order);
        paymentCard.setStatus(PaymentStatus.PROCESSING);
        paymentCard.setCardNumber("5123716344050150");

        entityManager.getTransaction().begin();
        entityManager.persist(paymentCard);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Order checkOrder = entityManager.find(Order.class, order.getId());

        Assert.assertNotNull(checkOrder.getPayment());
    }

}
