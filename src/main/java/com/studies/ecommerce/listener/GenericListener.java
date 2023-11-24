package com.studies.ecommerce.listener;

import javax.persistence.PostLoad;

public class GenericListener {

    @PostLoad
    public void logLoading(Object obj) {
        System.out.println("==> Entity " + obj.getClass().getSimpleName() + " was loaded.");
    }
}
