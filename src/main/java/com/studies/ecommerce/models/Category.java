package com.studies.ecommerce.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tab_category")
public class Category {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "table_generator")
    @TableGenerator(name = "table_generator", table = "hibernate_sequences",
            pkColumnName = "sequence_name",
            pkColumnValue = "category",
            valueColumnName = "next_val",
            initialValue = 0,
            allocationSize = 50)
    private Integer id;

    private String name;

    @Column(name = "category_father_id")
    private Integer categoryFatherId;
}
