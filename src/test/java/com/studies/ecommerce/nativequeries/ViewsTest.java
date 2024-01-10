package com.studies.ecommerce.nativequeries;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Client;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class ViewsTest extends EntityManagerTest {

    @Test
    public void runningView() {
        Query query = entityManager.createNativeQuery(
                "select cli.id, cli.name, sum(ord.total) " +
                        " from tab_order ord " +
                        " join clients_who_bought_above_average_view cli on cli.id = ord.client_id " +
                        " group by ord.client_id");

        List<Object[]> list = query.getResultList();

        list.forEach(arr -> System.out.printf("Client => ID: %s, Name: %s, Total: %s%n", arr));
    }

    @Test
    public void runningViewAndReturningClient() {
        Query query = entityManager.createNativeQuery(
                "select * from clients_who_bought_above_average_view", Client.class);

        List<Client> list = query.getResultList();

        list.forEach(obj -> System.out.printf("Client => ID: %s, Name: %s%n", obj.getId(), obj.getName()));
    }

}
