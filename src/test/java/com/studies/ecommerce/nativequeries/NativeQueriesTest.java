package com.studies.ecommerce.nativequeries;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Product;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class NativeQueriesTest extends EntityManagerTest {

    @Test
    public void passingParameters() {
        String sql = "select prd_id id, prd_name name, prd_description description, " +
        "            prd_created_at created_at, prd_updated_at updated_at, " +
        "            prd_price price, prd_product_photo product_photo " +
        " from tab_product_ecm where prd_id = :id";

        Query query = entityManager.createNativeQuery(sql, Product.class);
        query.setParameter("id", 201);

        List<Product> list = query.getResultList();

        list.stream().forEach(obj -> System.out.println(
                String.format("Product => ID: %s, Nome: %s", obj.getId(), obj.getName())));
    }

    @Test
    public void executeNativeSQLAndReturnEntity() {
//        String sql = "select id, name, price, created_at, updated_at, description, product_photo" +
//                " from tab_product_store";

//        String sql = "select prd_id id, prd_name name, prd_description description, " +
//                "            prd_created_at created_at, prd_updated_at updated_at, " +
//                "            prd_price price, prd_product_photo product_photo " +
//                " from tab_product_ecm";

        String sql = "select id, name, description, " +
                "            null created_at, null updated_at, " +
                "            price, null product_photo " +
                " from tab_product_erp";

        Query query = entityManager.createNativeQuery(sql, Product.class);

        List<Product> list = query.getResultList();

        list.stream().forEach(obj -> System.out.println(
                String.format("Product => ID: %s, Name: %s", obj.getId(), obj.getName())));
    }

    @Test
    public void executeNativeSQL() {
        String sql = "select id, name from tab_product order by id desc";
        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> list = query.getResultList();

        list.stream().forEach(arr -> System.out.println(
                String.format("Product => ID: %s, Name: %s", arr[0], arr[1])));
    }

}
