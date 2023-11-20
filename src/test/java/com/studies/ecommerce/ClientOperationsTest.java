package com.studies.ecommerce;

import com.studies.ecommerce.models.Client;
import org.junit.Assert;
import org.junit.Test;

public class ClientOperationsTest extends EntityManagerTest {

    @Test
    public void objectInsert() {
        Client client = new Client();

        client.setName("Roxana Brylee");

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

        entityManager.getTransaction().begin();
        entityManager.merge(client);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client checkClient = entityManager.find(Client.class, client.getId());

        Assert.assertEquals("Joetta Luann", checkClient.getName());
    }

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
