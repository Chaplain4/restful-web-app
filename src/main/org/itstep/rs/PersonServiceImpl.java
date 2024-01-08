package main.org.itstep.rs;

import com.sun.jersey.api.JResponse;
import main.org.itstep.rs.model.Person;
import main.org.itstep.rs.model.Response;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/persons")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class PersonServiceImpl implements PersonService {
    private static List<Person> personList = new ArrayList<>();
    static {
        personList.add(new Person(132, "John", 22));
    }

    @Override
    @POST
    @Path("/create")
    public Response createPerson(Person person) {
        Response response = new Response();
        int newId = person.getId();
        personList.forEach(person1 -> {
            if (person1.getId() == newId) {
                response.setStatus(false);
                response.setMsg("Person with id " + newId + " already exists!");
            }
        });
        if (response.getMsg() != null) {
            return response;
        }
        personList.add(person);
        response.setStatus(true);
        response.setMsg("Person with id " + newId + " successfully created!");
        return response;
    }

    @Override
    public Response deletePerson(int id) {
        return null;
    }

    @Override
    @GET
    @Path("/get/{id}")
    public Person getPerson(@PathParam("id") int id) {
        Response response = new Response();
        Person person = new Person();
        for (Person p : personList) {
            if(p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Person> getAllPersons() {
        return null;
    }
}
