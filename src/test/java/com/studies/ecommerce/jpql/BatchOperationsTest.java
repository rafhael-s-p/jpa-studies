package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Product;
import org.junit.Test;

import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class BatchOperationsTest extends EntityManagerTest {

    private static final int INSERT_LIMIT = 4;

    @Test
    public void batchRemove() {
        entityManager.getTransaction().begin();

        String jpql = "delete from Product p where p.id between 8 and 12";

        Query query = entityManager.createQuery(jpql);
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }

    @Test
    public void batchUpdate() {
        entityManager.getTransaction().begin();

        String jpql = "update Product p set p.price = p.price + (p.price * 0.1) " +
                " where exists (select 1 from p.categories c2 where c2.id = :category)";

        Query query = entityManager.createQuery(jpql);
        query.setParameter("category", 2);
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }

    @Test
    public void batchInsert() {
        InputStream in = BatchOperationsTest.class.getClassLoader()
                .getResourceAsStream("products/products-import.txt");

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        entityManager.getTransaction().begin();

        int insertCount = 0;
        int batchCount = 0;

        for (String line: reader.lines().collect(Collectors.toList())) {
            if (line.isBlank())
                continue;

            String[] columnProduct = line.split(";");
            Product product = new Product();
            product.setName(columnProduct[0]);
            product.setDescription(columnProduct[1]);
            product.setPrice(new BigDecimal(columnProduct[2].replaceAll("\\s", "")));
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());

            entityManager.persist(product);

            if (++insertCount == INSERT_LIMIT) {
                entityManager.flush();
                entityManager.clear();
                System.out.println("--------------------------------- BATCH: "+ ++batchCount +" ---------------------------------");
                insertCount = 0;

            }
        }

        entityManager.getTransaction().commit();
    }

}
