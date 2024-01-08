package main.org.itstep.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path("/test")
public class TestService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Server time is " + new Date();
    }
}
