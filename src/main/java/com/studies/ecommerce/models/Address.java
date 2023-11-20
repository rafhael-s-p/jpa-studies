package com.studies.ecommerce.models;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Address {

    private String postal;

    private String street;

    private String number;

    private String complement;

    private String district;

    private String city;

    private String state;

}
