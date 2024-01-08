package main.org.itstep.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Path("/test")
public class TestService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Server time is " + new Date();
    }

    @GET
    @Path("/xml")
    @Produces(MediaType.APPLICATION_XML)
    public String helloXML() {
        return "<server><time>" + new Date().toString() + "</time></server>";
    }

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public String helloJSON() {
        return "{  \"ServerTime\": \n" +
                "    {\n" +
                "      \"Time\": " + new Date() + "\n" +
                "    }}";
    }
}
