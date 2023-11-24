package com.studies.ecommerce.service;

import com.studies.ecommerce.models.Order;

public class InvoiceService {

    public void generate(Order order) {
        System.out.println("==> Generating invoice for order " + order.getId());
    }

}
