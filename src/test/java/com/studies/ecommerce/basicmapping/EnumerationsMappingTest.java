package com.studies.ecommerce.basicmapping;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Client;
import com.studies.ecommerce.models.Gender;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class EnumerationsMappingTest extends EntityManagerTest {

    @Test
    public void enumTest() {
        Client client = new Client();

        client.setName("Kristie Jeanne");
        client.setSsn("486242016");
        client.setGender(Gender.FEMALE);
        client.setBirthday(LocalDate.of(1980, 8, 10));

        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Client checkClient = entityManager.find(Client.class, client.getId());

        Assert.assertNotNull(checkClient);
        Assert.assertEquals(checkClient.getGender(), Gender.FEMALE);
    }
}
