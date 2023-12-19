package com.studies.ecommerce.jpql;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Product;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

public class DynamicQueryTest extends EntityManagerTest {

    @Test
    public void dynamicQuery() {
        Product product = new Product();
        product.setName("GoPro Hero");

        List<Product> list = search(product);

        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals("GoPro Hero 7 Camera", list.get(0).getName());
    }

    private List<Product> search(Product product) {
        StringBuilder jpql = new StringBuilder("select p from Product p where 1 = 1");

        if (product.getName() != null)
            jpql.append(" and p.name like concat('%', :name, '%')");

        if (product.getDescription() != null)
            jpql.append(" and p.description like concat('%', :description, '%')");

        TypedQuery<Product> typedQuery = entityManager.createQuery(jpql.toString(), Product.class);

        if (product.getName() != null)
            typedQuery.setParameter("name", product.getName());

        if (product.getDescription() != null)
            typedQuery.setParameter("description", product.getDescription());

        return typedQuery.getResultList();
    }

}
