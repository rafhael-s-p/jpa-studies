package com.studies.ecommerce.nativequeries;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Client;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.util.List;

public class StoredProceduresTest extends EntityManagerTest {

    public static final Integer PRODUCT_ID = 1;
    public static final BigDecimal TEN_PERCENT_OFF = BigDecimal.valueOf(0.1);

    @Test
    public void namedStoredProcedure() {
        StoredProcedureQuery storedProcedureQuery = entityManager
                .createNamedStoredProcedureQuery("clients_who_bought_above_average");

        storedProcedureQuery.setParameter("sell_year", 2024);

        List<Client> list = storedProcedureQuery.getResultList();

        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void adjustPriceProduct() {
        StoredProcedureQuery storedProcedureQuery = entityManager
                .createStoredProcedureQuery("adjust_price_product", Client.class);

        storedProcedureQuery.registerStoredProcedureParameter("product_id", Integer.class, ParameterMode.IN);

        storedProcedureQuery.registerStoredProcedureParameter("percentage_adjustment", BigDecimal.class, ParameterMode.IN);

        storedProcedureQuery.registerStoredProcedureParameter("adjusted_price", BigDecimal.class, ParameterMode.OUT);

        storedProcedureQuery.setParameter("product_id", PRODUCT_ID);
        storedProcedureQuery.setParameter("percentage_adjustment", TEN_PERCENT_OFF);

        BigDecimal adjustedPrice = (BigDecimal) storedProcedureQuery
                .getOutputParameterValue("adjusted_price");

        Assert.assertEquals(new BigDecimal("878.9"), adjustedPrice);
    }

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
