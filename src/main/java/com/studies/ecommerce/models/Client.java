package com.studies.ecommerce.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tab_client")
public class Client {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ElementCollection
    @CollectionTable(name = "tab_client_contact",
            joinColumns = @JoinColumn(name = "client_id"))
    @MapKeyColumn(name = "type")
    @Column(name = "description")
    private Map<String, String> contacts;

    @Transient
    private String firstName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    @PostLoad
    public void gettingFirstName(){
        if (name != null && !name.isBlank()) {
            int index = name.indexOf(" ");
            if (index > -1)
                firstName = name.substring(0, index);
        }
    }

}
