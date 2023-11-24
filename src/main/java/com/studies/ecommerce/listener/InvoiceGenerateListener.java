package com.studies.ecommerce.listener;

import com.studies.ecommerce.models.Order;
import com.studies.ecommerce.service.InvoiceService;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class InvoiceGenerateListener {

    private InvoiceService invoiceService = new InvoiceService();

    @PrePersist
    @PreUpdate
    public void generate(Order order) {
        if (order.isPaid() && order.getInvoice() == null)
            invoiceService.generate(order);
    }
}
