package com.studies.ecommerce.hibernate;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class EcmCurrentTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void setTenantIdentifier(String tenantId) {
        threadLocal.set(tenantId);
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        return threadLocal.get();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }

}
