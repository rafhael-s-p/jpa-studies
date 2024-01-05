package com.studies.ecommerce.nativequeries;

import com.studies.ecommerce.EntityManagerTest;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class NativeQueriesTest extends EntityManagerTest {

    @Test
    public void executeNativeSQL() {
        String sql = "select id, name from tab_product order by id desc";
        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> list = query.getResultList();

        list.stream().forEach(arr -> System.out.println(
                String.format("Product => ID: %s, Name: %s", arr[0], arr[1])));
    }

}
