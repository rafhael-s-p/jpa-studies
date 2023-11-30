package com.studies.ecommerce.advancedmapping;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Client;
import org.junit.Assert;
import org.junit.Test;

public class InheritanceTest extends EntityManagerTest {

    @Test
    public void saveClient() {
        Client client = new Client();
        client.setName("Mary Jane");

        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client checkClient = entityManager.find(Client.class, client.getId());

        Assert.assertNotNull(checkClient.getId());
    }

}
