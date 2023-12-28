package com.studies.ecommerce.criteria;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.*;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class CriteriaFunctionsTest extends EntityManagerTest {

    @Test
    public void applyNativeFunctions() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.multiselect(
                root.get(Order_.id),
                criteriaBuilder.function("dayname", String.class, root.get(Order_.createdAt))
        );

        criteriaQuery.where(criteriaBuilder.isTrue(
                criteriaBuilder.function("average_above_revenue", Boolean.class, root.get(Order_.total))));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(arr[0] + ", dayname: " + arr[1]));
    }

    @Test
    public void applyCollectionFunctions() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.multiselect(
                root.get(Order_.id),
                criteriaBuilder.size(root.get(Order_.items))
        );

        criteriaQuery.where(criteriaBuilder.greaterThan(
                criteriaBuilder.size(root.get(Order_.items)), 1));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(arr[0] + ", size: " + arr[1]));
    }

    @Test
    public void applyNumericFunctions() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Order> root = criteriaQuery.from(Order.class);

        criteriaQuery.multiselect(
                root.get(Order_.id),
                criteriaBuilder.abs(criteriaBuilder.prod(root.get(Order_.id), -1)),
                criteriaBuilder.mod(root.get(Order_.id), 2),
                criteriaBuilder.sqrt(root.get(Order_.total))
        );

        criteriaQuery.where(criteriaBuilder.greaterThan(
                criteriaBuilder.sqrt(root.get(Order_.total)), 10.0));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(
                arr[0]
                        + ", abs: " + arr[1]
                        + ", mod: " + arr[2]
                        + ", sqrt: " + arr[3]));
    }

    @Test
    public void applyDateFunctions() {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Order> root = criteriaQuery.from(Order.class);
        Join<Order, Payment> joinPayment = root.join(Order_.payment);
        Join<Order, PaymentSlip> joinPaymentSlip = criteriaBuilder.treat(joinPayment, PaymentSlip.class);

        criteriaQuery.multiselect(root.get(Order_.id),
                criteriaBuilder.currentDate(),
                criteriaBuilder.currentTime(),
                criteriaBuilder.currentTimestamp()
        );

        criteriaQuery.where(criteriaBuilder.between(root.get(Order_.createdAt).as(java.sql.Date.class),
                        joinPaymentSlip.get(PaymentSlip_.dueDate).as(java.sql.Date.class),
                        criteriaBuilder.currentDate()),
                criteriaBuilder.equal(root.get(Order_.status), OrderStatus.WAITING)
        );

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(arr[0]
                        + ", current_date: " + arr[1]
                        + ", current_time: " + arr[2]
                        + ", current_timestamp: " + arr[3]));
    }

    @Test
    public void applyStringFunctions() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Client> root = criteriaQuery.from(Client.class);

        criteriaQuery.multiselect(
                root.get(Client_.name),
                criteriaBuilder.concat("Client name: ", root.get(Client_.name)),
                criteriaBuilder.length(root.get(Client_.name)),
                criteriaBuilder.locate(root.get(Client_.name), "a"),
                criteriaBuilder.substring(root.get(Client_.name), 1, 2),
                criteriaBuilder.lower(root.get(Client_.name)),
                criteriaBuilder.upper(root.get(Client_.name)),
                criteriaBuilder.trim(root.get(Client_.name))
        );

        criteriaQuery.where(criteriaBuilder.equal(
                criteriaBuilder.substring(root.get(Client_.name), 1, 1), "G"));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Object[]> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());

        list.forEach(arr -> System.out.println(
                arr[0]
                        + ", concat: " + arr[1]
                        + ", length: " + arr[2]
                        + ", locate: " + arr[3]
                        + ", substring: " + arr[4]
                        + ", lower: " + arr[5]
                        + ", upper: " + arr[6]
                        + ", trim: |" + arr[7] + "|"));
    }

}