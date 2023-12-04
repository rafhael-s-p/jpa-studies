package com.studies.ecommerce.models;

import com.studies.ecommerce.listener.GenericListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EntityListeners({ GenericListener.class })
@Entity
@Table(name = "tab_product",
        uniqueConstraints = { @UniqueConstraint(name = "unq_name", columnNames = { "name" }) },
        indexes = { @Index(name = "idx_name", columnList = "name") })
public class Product extends BaseEntity {

    private String name;

    private String description;

    private BigDecimal price;

    @Lob
    private byte[] productPhoto;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "tab_product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @OneToOne(mappedBy = "product")
    private Warehouse warehouse;

    @ElementCollection
    @CollectionTable(name = "tab_product_tag",
            joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag")
    private List<String> tags;

    @ElementCollection
    @CollectionTable(name = "tab_product_characteristic",
            joinColumns = @JoinColumn(name = "product_id"))
    private List<ProductCharacteristic> characteristics;

}
