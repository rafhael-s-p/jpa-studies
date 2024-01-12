package com.studies.ecommerce.models;

import com.studies.ecommerce.dto.ProductDTO;
import com.studies.ecommerce.listener.GenericListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NamedNativeQueries({
        @NamedNativeQuery(name = "product_store.list",
                query = "select id, name, description, created_at, updated_at, price, product_photo " +
                        " from tab_product_store", resultClass = Product.class),
        @NamedNativeQuery(name = "product_ecm.list",
                query = "select * from tab_product_ecm",
                resultSetMapping = "product_ecm.Product")
})
@SqlResultSetMappings({
        @SqlResultSetMapping(name = "product_store.Product",
                entities = { @EntityResult(entityClass = Product.class) }),
        @SqlResultSetMapping(name = "product_ecm.Product",
                entities = { @EntityResult(entityClass = Product.class,
                        fields = {
                                @FieldResult(name = "id", column = "prd_id"),
                                @FieldResult(name = "name", column = "prd_name"),
                                @FieldResult(name = "description", column = "prd_description"),
                                @FieldResult(name = "price", column = "prd_price"),
                                @FieldResult(name = "product_photo", column = "prd_product_photo"),
                                @FieldResult(name = "created_at", column = "prd_created_at"),
                                @FieldResult(name = "updated_at", column = "prd_updated_at")
                        }) }),
        @SqlResultSetMapping(name = "product_ecm.ProductDTO",
                classes = {
                        @ConstructorResult(targetClass = ProductDTO.class,
                                columns = {
                                        @ColumnResult(name = "prd_id", type = Integer.class),
                                        @ColumnResult(name = "prd_name", type = String.class)
                                })
                })
})
@NamedQueries({
        @NamedQuery(name = "Product.list", query = "select p from Product p"),
        @NamedQuery(name = "Product.listByCategory", query = "select p from Product p where exists (select 1 from Category c2 join c2.products p2 where p2 = p and c2.id = :category)")
})
@EntityListeners({ GenericListener.class })
@Entity
@Table(name = "tab_product",
        uniqueConstraints = { @UniqueConstraint(name = "unq_name", columnNames = { "name" }) },
        indexes = { @Index(name = "idx_product_name", columnList = "name") })
public class Product extends BaseEntity {

    @NotBlank
    @Column(length = 100, nullable = false) // default length 255
    private String name;

    @Lob
    private String description;

    @Column(precision = 18, scale = 2) // Default precision, scale is 19, 2
    private BigDecimal price;

    @Lob
    @Column(name = "product_photo")
    private byte[] productPhoto;

    @PastOrPresent
    @NotNull
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @PastOrPresent
    @NotNull
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "tab_product_category",
            joinColumns = @JoinColumn(name = "product_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_product_category_product")),
            inverseJoinColumns = @JoinColumn(name = "category_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_product_category_category")))
    private List<Category> categories;

    @OneToOne(mappedBy = "product")
    private Warehouse warehouse;

    @ElementCollection
    @CollectionTable(name = "tab_product_tag",
            joinColumns = @JoinColumn(name = "product_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_product_tag_product")))
    @Column(name = "tag")
    private List<String> tags;

    @ElementCollection
    @CollectionTable(name = "tab_product_characteristic",
            joinColumns = @JoinColumn(name = "product_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_product_characteristic_product")))
    private List<ProductCharacteristic> characteristics;

}
