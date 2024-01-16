package com.studies.ecommerce.other;

import com.studies.ecommerce.EntityManagerTest;
import com.studies.ecommerce.models.Client;
import com.studies.ecommerce.models.Order;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityGraph;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;
import java.util.List;

public class EntityGraphTest extends EntityManagerTest {

    @Test
    public void findEssentialAttributesOfOrder02() {
        EntityGraph<Order> entityGraph = entityManager.createEntityGraph(Order.class);
        entityGraph.addAttributeNodes(
                "createdAt", "status", "total");

        Subgraph<Client> subgraphClient = entityGraph.addSubgraph("client", Client.class);
        subgraphClient.addAttributeNodes("name", "ssn");

        TypedQuery<Order> typedQuery = entityManager.createQuery("select o from Order o", Order.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

    @Test
    public void findEssentialAttributesOfOrder() {
        EntityGraph<Order> entityGraph = entityManager.createEntityGraph(Order.class);
        entityGraph.addAttributeNodes("createdAt", "status", "total", "invoice");
        /*
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
//        properties.put("javax.persistence.loadgraph", entityGraph);
        Order order = entityManager.find(Order.class, 1, properties);
        Assert.assertNotNull(order);
        */

        TypedQuery<Order> typedQuery = entityManager.createQuery("select o from Order o", Order.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        List<Order> list = typedQuery.getResultList();
        Assert.assertFalse(list.isEmpty());
    }

}
