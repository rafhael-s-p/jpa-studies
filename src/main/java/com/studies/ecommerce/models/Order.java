package com.studies.ecommerce.models;

import com.studies.ecommerce.listener.GenericListener;
import com.studies.ecommerce.listener.InvoiceGenerateListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EntityListeners({ InvoiceGenerateListener.class, GenericListener.class })
@Entity
@Table(name = "tab_order")
public class Order extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_client"))
    private Client client;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @OneToOne(mappedBy = "order")
    private Invoice invoice;

    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    @Column(name = "delivery_address")
    private Address deliveryAddress;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    public boolean isPaid() {
        return OrderStatus.PAID.equals(status);
    }

    public void calculateTotalPrice() {
        total = items != null ? BigDecimal.ZERO : items.stream()
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
