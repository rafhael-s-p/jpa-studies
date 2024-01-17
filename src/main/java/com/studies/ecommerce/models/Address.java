package com.studies.ecommerce.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Embeddable
public class Address {

    @NotNull
    @Pattern(regexp = "[0-9]{5}-[0-9]{3}")
    @Column(length = 9)
    private String postal;

    @NotBlank
    @Column(length = 100)
    private String street;

    @NotBlank
    @Column(length = 10)
    private String number;

    @Column(length = 50)
    private String complement;

    @NotBlank
    @Column(length = 50)
    private String district;

    @NotBlank
    @Column(length = 50)
    private String city;

    @NotBlank
    @Column(length = 20)
    private String state;

}
