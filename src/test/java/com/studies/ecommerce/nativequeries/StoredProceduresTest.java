package com.studies.ecommerce.nativequeries;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Client;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class StoredProceduresTest extends EntityManagerTest {

    public static final Integer PRODUCT_ID = 1;

    @Test
    public void receivingListAsProcedure() {
        StoredProcedureQuery storedProcedureQuery = entityManager
                .createStoredProcedureQuery("clients_who_bought_above_average", Client.class);

        storedProcedureQuery.registerStoredProcedureParameter( "sell_year", Integer.class, ParameterMode.IN);

        storedProcedureQuery.setParameter("sell_year", 2024);

        List<Client> list = storedProcedureQuery.getResultList();

        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void usingParametersInAndOut() {
        StoredProcedureQuery storedProcedureQuery = entityManager
                .createStoredProcedureQuery("find_product_name");

        storedProcedureQuery.registerStoredProcedureParameter("product_id", Integer.class, ParameterMode.IN);

        storedProcedureQuery.registerStoredProcedureParameter("product_name", String.class, ParameterMode.OUT);

        storedProcedureQuery.setParameter("product_id", PRODUCT_ID);

        String name = (String) storedProcedureQuery.getOutputParameterValue("product_name");

        Assert.assertEquals("Kindle", name);
    }

}
