package com.studies.ecommerce.advancedmapping;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Client;
import org.junit.Assert;
import org.junit.Test;

public class TransientPropertiesTest extends EntityManagerTest {

    @Test
    public void checkFirstName() {
        Client client = entityManager.find(Client.class, 1);

        Assert.assertEquals("Jenni", client.getFirstName());
    }
}
