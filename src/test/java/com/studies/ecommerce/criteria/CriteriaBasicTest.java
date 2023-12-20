package com.studies.ecommerce.criteria;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.dto.ProductDTO;
import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.models.Product;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

public class CriteriaBasicTest extends EntityManagerTest {

    @Test
    public void returnDTOProjection() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductDTO> criteriaQuery = criteriaBuilder.createQuery(ProductDTO.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(criteriaBuilder
                .construct(ProductDTO.class, root.get("id"), root.get("name")));

        TypedQuery<ProductDTO> typedQuery = entityManager.createQuery(criteriaQuery);
        List<ProductDTO> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(dto -> System.out.println("ID: " + dto.getId() + ", Name: " + dto.getName()));
    }

    @Test
    public void returnTupleProjection() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(criteriaBuilder
                .tuple(root.get("id").alias("alias_id"), root.get("name").alias("alias_name")));

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Tuple> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(t -> System.out.println("ID: " + t.get("alias_id") + ", Name: " + t.get("alias_name")));
    }

    @Test
    public void returnProjection() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.multiselect(root.get("id"), root.get("name"));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println("ID: " + arr[0] + ", Name: " + arr[1]));
    }

    @Test
    public void returnAllProducts() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Product> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void returnAttribute() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root.get("total"));

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<BigDecimal> typedQuery = entityManager.createQuery(criteriaQuery);
        BigDecimal total = typedQuery.getSingleResult();
        Assert.assertEquals(new BigDecimal("2398.00"), total);
    }

    @Test
    public void findById() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);

        Order order = typedQuery.getSingleResult();
        Assert.assertNotNull(order);
    }

}
