package com.studies.ecommerce.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class ExecuteDDL {

    public static void main(String[] args) {
        Map<String, String> properties = new HashMap<>();

        properties.put("javax.persistence.schema-generation.database.action",
                "drop-and-create");

        properties.put("javax.persistence.schema-generation.create-source",
                "metadata-then-script");
        properties.put("javax.persistence.schema-generation.drop-source",
                "metadata-then-script");

        properties.put("javax.persistence.schema-generation.create-script-source",
                "META-INF/database/create.sql");
        properties.put("javax.persistence.schema-generation.drop-script-source",
                "META-INF/database/drop.sql");

        properties.put("javax.persistence.sql-load-script-source",
                "META-INF/database/initial-data.sql");

        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("Ecommerce-PU", properties);

        entityManagerFactory.close();
    }

}