package com.studies.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tab_category",
        uniqueConstraints = { @UniqueConstraint(name = "unq_name", columnNames = { "name" }) },
        indexes = { @Index(name = "idx_name", columnList = "name") })
public class Category extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_father_id",
            foreignKey = @ForeignKey(name = "fk_category_categoryfather"))
    private Category categoryFather;

    @OneToMany(mappedBy = "categoryFather")
    private List<Category> categories;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products;

}
