package main.org.itstep.rs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private boolean status;
    private String msg;
}
