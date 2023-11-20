package com.studies.ecommerce;

import com.studies.ecommerce.models.Client;
import com.studies.ecommerce.models.Gender;
import org.junit.Assert;
import org.junit.Test;

public class EnumerationsMappingTest extends EntityManagerTest {

    @Test
    public void enumTest() {
        Client cliente = new Client();

        cliente.setName("Kristie Jeanne");
        cliente.setGender(Gender.FEMALE);

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client checkClient = entityManager.find(Client.class, cliente.getId());

        Assert.assertNotNull(checkClient);
        Assert.assertEquals(checkClient.getGender(), Gender.FEMALE);
    }
}
