package com.studies.ecommerce.models;

import com.studies.ecommerce.listener.GenericListener;
import com.studies.ecommerce.listener.InvoiceGenerateListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EntityListeners({ InvoiceGenerateListener.class, GenericListener.class })
@Entity
@Table(name = "tab_order")
public class Order extends BaseEntity implements PersistentAttributeInterceptable {

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_client"))
    private Client client;

    @NotEmpty
    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

    @PastOrPresent
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @PastOrPresent
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @PastOrPresent
    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private Invoice invoice;

    @NotNull
    @Positive
    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal total;

    @NotNull
    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    @Column(name = "delivery_address")
    private Address deliveryAddress;

    @LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private Payment payment;

    public Invoice getInvoice() {
        if (this.persistentAttributeInterceptor != null)
            return (Invoice) persistentAttributeInterceptor
                    .readObject(this, "invoice", this.invoice);

        return this.invoice;
    }

    public void setInvoice(Invoice invoice) {
        if (this.persistentAttributeInterceptor != null) {
            this.invoice = (Invoice) persistentAttributeInterceptor
                    .writeObject(this, "invoice", this.invoice, invoice);
        } else {
            this.invoice = invoice;
        }
    }

    public Payment getPayment() {
        if (this.persistentAttributeInterceptor != null)
            return (Payment) persistentAttributeInterceptor
                    .readObject(this, "payment", this.payment);

        return this.payment;
    }

    public void setPayment(Payment payment) {
        if (this.persistentAttributeInterceptor != null) {
            this.payment = (Payment) persistentAttributeInterceptor
                    .writeObject(this, "payment", this.payment, payment);
        } else {
            this.payment = payment;
        }
    }

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @Transient
    private PersistentAttributeInterceptor persistentAttributeInterceptor;

    @Override
    public PersistentAttributeInterceptor $$_hibernate_getInterceptor() {
        return this.persistentAttributeInterceptor;
    }

    @Override
    public void $$_hibernate_setInterceptor(PersistentAttributeInterceptor persistentAttributeInterceptor) {
        this.persistentAttributeInterceptor = persistentAttributeInterceptor;
    }

    public boolean isPaid() {
        return OrderStatus.PAID.equals(status);
    }

    public void calculateTotalPrice() {
        total = items == null ? BigDecimal.ZERO : items.stream()
                    .map(item  -> new BigDecimal(item.getAmount()).multiply(item.getProductPrice()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @PrePersist
    public void whenToPersist() {
        createdAt = LocalDateTime.now();
        calculateTotalPrice();
    }

    @PreUpdate
    public void whenToUpdate() {
        updatedAt = LocalDateTime.now();
        calculateTotalPrice();
    }

    @PostPersist
    public void afterToPersist() {
        System.out.println("==> After persisting Order. ==> @PostPersist");
    }

    @PostUpdate
    public void afterToUpdate() {
        System.out.println("==> After updating Order. ==> @PostUpdate");
    }

    @PreRemove
    public void whenToRemove() {
        System.out.println("==> Before removing Order. ==> @PreRemove");
    }

    @PostRemove
    public void afterToRemove() {
        System.out.println("==> After removing Order. ==> @PostRemove");
    }

    @PostLoad
    public void whenToLoad() {
        System.out.println("==> After loading the Order. ==> @PostLoad");
    }

}
