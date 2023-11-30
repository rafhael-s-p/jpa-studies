package com.studies.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tab_category")
public class Category extends BaseEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_father_id")
    private Category categoryFather;

    @OneToMany(mappedBy = "categoryFather")
    private List<Category> categories;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products;

}
