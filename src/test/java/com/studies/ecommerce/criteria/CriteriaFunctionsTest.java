package com.studies.ecommerce.criteria;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Client;
import com.studies.ecommerce.models.Client_;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CriteriaFunctionsTest extends EntityManagerTest {

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