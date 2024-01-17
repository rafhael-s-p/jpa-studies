package com.studies.ecommerce.other;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Order;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityGraph;
import java.util.List;

public class NPlus1BugTest extends EntityManagerTest {

    @Test
    public void entityGraphSolution() {
        EntityGraph<Order> entityGraph = entityManager.createEntityGraph(Order.class);
        entityGraph.addAttributeNodes("client", "invoice", "payment");

        List<Order> list = entityManager
                .createQuery("select o from Order o ", Order.class)
                .setHint("javax.persistence.loadgraph", entityGraph)
                .getResultList();

        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void fetchSolution() {
        List<Order> list = entityManager
                .createQuery("select o from Order o " +
                        " join fetch o.client c " +
                        " join fetch o.payment p " +
                        " join fetch o.invoice inv", Order.class)
                .getResultList();

        Assert.assertFalse(list.isEmpty());
    }

}
