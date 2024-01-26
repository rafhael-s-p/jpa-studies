package com.studies.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NamedStoredProcedureQuery(name = "clients_who_bought_above_average", procedureName = "clients_who_bought_above_average",
        parameters = {
                @StoredProcedureParameter(name = "sell_year", type = Integer.class, mode = ParameterMode.IN)
        },
        resultClasses = Client.class
)
@SecondaryTable(name = "tab_client_detail",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "client_id"),
        foreignKey = @ForeignKey(name = "fk_client_detail_client"))
@Entity
@Table(name = "tab_client",
        uniqueConstraints = { @UniqueConstraint(name = "unq_client_ssn", columnNames = { "ssn" }) },
        indexes = { @Index(name = "idx_client_name", columnList = "name") })
public class Client extends BaseEntity {

    @NotBlank
    @Column(length = 100, nullable = false)
    private String name;

    @NotNull
    //@Pattern(regexp = "(^\\d{3}\\x2D\\d{2}\\x2D\\d{4}$)")
    @Column(length = 11, nullable = false)
    private String ssn;

    @ElementCollection
    @CollectionTable(name = "tab_client_contact",
            joinColumns = @JoinColumn(name = "client_id", nullable = false,
                    foreignKey = @ForeignKey(name = "fk_client_contact_client")))
    @MapKeyColumn(name = "type")
    @Column(name = "description")
    private Map<String, String> contacts;

    @Transient
    private String firstName;

    @NotNull
    @Column(table = "tab_client_detail")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Past
    @Column(table = "tab_client_detail")
    private LocalDate birthday;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    @PostLoad
    public void gettingFirstName() {
        if (name != null && !name.isBlank()) {
            int index = name.indexOf(" ");
            if (index > -1)
                firstName = name.substring(0, index);
        }
    }

}
