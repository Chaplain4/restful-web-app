package main.org.itstep.rs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private int id;
    private String location;
    private int postalCode;
}
