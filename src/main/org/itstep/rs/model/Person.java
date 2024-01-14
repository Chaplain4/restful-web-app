package main.org.itstep.rs.model;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "person")
@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "persons")
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @ManyToOne(fetch = FetchType.EAGER)
    private Address address;
}
