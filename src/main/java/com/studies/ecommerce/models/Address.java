package com.studies.ecommerce.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class Address {

    @Column(length = 9)
    private String postal;

    @Column(length = 100)
    private String street;

    @Column(length = 10)
    private String number;

    @Column(length = 50)
    private String complement;

    @Column(length = 50)
    private String district;

    @Column(length = 50)
    private String city;

    @Column(length = 2)
    private String state;

}
