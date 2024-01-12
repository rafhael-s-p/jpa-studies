package com.studies.ecommerce.jpastarting;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Client;
import com.studies.ecommerce.models.Gender;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;

public class ClientOperationsTest extends EntityManagerTest {

    @Test
    public void objectInsert() {
        Client client = new Client();
        client.setName("Roxana Brylee");
        client.setSsn("036582306");
        client.setGender(Gender.FEMALE);
        client.setBirthday(LocalDate.of(1980, 8, 10));

        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Client checkClient = entityManager.find(Client.class, client.getId());

        Assert.assertNotNull(checkClient);
    }

    @Test
    public void findById() {
        Client client = entityManager.find(Client.class, 1);

        Assert.assertNotNull(client);
        Assert.assertEquals("Jenni Jacklyn", client.getName());
    }

    @Test
    public void objectUpdate() {
        Client client = new Client();
        client.setId(4);
        client.setName("Joetta Luann");
        client.setSsn("525242710");
        client.setGender(Gender.FEMALE);
        client.setBirthday(LocalDate.of(1980, 8, 10));

        entityManager.getTransaction().begin();
        entityManager.merge(client);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Client checkClient = entityManager.find(Client.class, client.getId());

        Assert.assertEquals("Joetta Luann", checkClient.getName());
    }

    @Ignore
    @Test
    public void objectDelete() {
        Client client = entityManager.find(Client.class, 2);

        entityManager.getTransaction().begin();
        entityManager.remove(client);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Client checkClient = entityManager.find(Client.class, client.getId());
        Assert.assertNull(checkClient);
    }

}
