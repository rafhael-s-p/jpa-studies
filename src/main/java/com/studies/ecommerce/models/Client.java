package com.studies.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@SecondaryTable(name = "tab_client_detail", pkJoinColumns = @PrimaryKeyJoinColumn(name = "client_id"))
@Entity
@Table(name = "tab_client",
        uniqueConstraints = { @UniqueConstraint(name = "unq_ssn", columnNames = { "ssn" }) },
        indexes = { @Index(name = "idx_name", columnList = "name") })
public class Client extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 11, nullable = false)
    private String ssn;

    @ElementCollection
    @CollectionTable(name = "tab_client_contact",
            joinColumns = @JoinColumn(name = "client_id"))
    @MapKeyColumn(name = "type")
    @Column(name = "description")
    private Map<String, String> contacts;

    @Transient
    private String firstName;

    @Column(table = "tab_client_detail")
    @Enumerated(EnumType.STRING)
    private Gender gender;

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
