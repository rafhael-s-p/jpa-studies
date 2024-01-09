package com.studies.ecommerce.nativequeries;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.dto.ProductDTO;
import com.studies.ecommerce.models.Category;
import com.studies.ecommerce.models.OrderItem;
import com.studies.ecommerce.models.Product;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.Query;
import java.util.List;

public class NativeQueriesTest extends EntityManagerTest {

    @Test
    public void namedNativeQueryUsingXMLFile() {
        Query query = entityManager.createNamedQuery("category_ecm.list");

        List<Category> list = query.getResultList();

        list.forEach(obj -> System.out.printf("Category => ID: %s, Name: %s%n", obj.getId(), obj.getName()));
    }

    @Ignore
    @Test
    public void namedNativeQuery02() {
        // It seems JPA has a bug and Field Result requires all fields in order to JPA do the cast, including relationships. It must be checked out later.
        Query query = entityManager.createNamedQuery("product_ecm.list");

        List<Product> list = query.getResultList();

        list.forEach(obj -> System.out.printf("Product => ID: %s, Name: %s%n", obj.getId(), obj.getName()));
    }

    @Test
    public void namedNativeQuery01() {
        Query query = entityManager.createNamedQuery("product_store.list");

        List<Product> list = query.getResultList();

        list.forEach(obj -> System.out.printf("Product => ID: %s, Name: %s%n", obj.getId(), obj.getName()));
    }

    @Test
    public void fieldResultReturningDTO() {
        String sql = "select * from tab_product_ecm";

        Query query = entityManager.createNativeQuery(sql, "product_ecm.ProductDTO");

        List<ProductDTO> list = query.getResultList();

        list.forEach(obj -> System.out.printf("ProductDTO => ID: %s, Name: %s%n", obj.getId(), obj.getName()));
    }

    @Ignore
    @Test
    public void fieldResult() {
        // It seems JPA has a bug and Field Result requires all fields in order to JPA do the cast, including relationships. It must be checked out later.
        String sql = "select * from tab_product_ecm";

        Query query = entityManager.createNativeQuery(sql, "product_ecm.Product");

        List<Product> list = query.getResultList();

        list.forEach(obj -> System.out.printf("Product => ID: %s, Name: %s%n", obj.getId(), obj.getName()));
    }

    @Test
    public void resultSetMapping02() {
        String sql = "select oi.*, p.* from tab_order_item oi join tab_product p on p.id = oi.product_id";

        Query query = entityManager.createNativeQuery(sql, "order_item-product.OrderItem-Product");

        List<Object[]> list = query.getResultList();

        list.forEach(arr -> System.out.printf("Order => ID: %s -- Product => ID: %s, Name: %s%n",
                ((OrderItem) arr[0]).getId().getOrderId(),
                ((Product)arr[1]).getId(), ((Product)arr[1]).getName()));
    }

    @Test
    public void resultSetMapping01() {
        String sql = "select id, name, description, created_at, updated_at, price, product_photo " +
                " from tab_product_store";

        Query query = entityManager.createNativeQuery(sql, "product_store.Product");

        List<Product> list = query.getResultList();

        list.forEach(obj -> System.out.printf("Product => ID: %s, Nome: %s%n", obj.getId(), obj.getName()));
    }

    @Test
    public void passingParameters() {
        String sql = "select prd_id id, prd_name name, prd_description description, " +
        "            prd_created_at created_at, prd_updated_at updated_at, " +
        "            prd_price price, prd_product_photo product_photo " +
        " from tab_product_ecm where prd_id = :id";

        Query query = entityManager.createNativeQuery(sql, Product.class);
        query.setParameter("id", 201);

        List<Product> list = query.getResultList();

        list.forEach(obj -> System.out.printf("Product => ID: %s, Nome: %s%n", obj.getId(), obj.getName()));
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

        list.forEach(obj -> System.out.printf("Product => ID: %s, Name: %s%n", obj.getId(), obj.getName()));
    }

    @Test
    public void executeNativeSQL() {
        String sql = "select id, name from tab_product order by id desc";
        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> list = query.getResultList();

        list.forEach(arr -> System.out.printf("Product => ID: %s, Name: %s%n", arr[0], arr[1]));
    }

}
