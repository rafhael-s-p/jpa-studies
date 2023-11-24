package com.studies.ecommerce.models;

import com.studies.ecommerce.listener.GenericListener;
import com.studies.ecommerce.listener.InvoiceGenerateListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners({ InvoiceGenerateListener.class, GenericListener.class })
@Entity
@Table(name = "tab_order")
public class Order {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @OneToOne(mappedBy = "order")
    private Invoice invoice;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    @Column(name = "delivery_address")
    private Address deliveryAddress;

    @OneToOne(mappedBy = "order")
    private CardPayment cardPayment;

    public boolean isPaid() {
        return OrderStatus.PAID.equals(status);
    }

    public void calculateTotalPrice() {
        if (items != null)
            total = items.stream().map(OrderItem::getProductPrice)
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
