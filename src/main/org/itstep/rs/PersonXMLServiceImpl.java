package main.org.itstep.rs;

import main.org.itstep.rs.model.Address;
import main.org.itstep.rs.model.Person;
import main.org.itstep.rs.model.Response;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/persons")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class PersonXMLServiceImpl implements PersonService {

    private static List<Person> personsList = new ArrayList<>();

    static {
        personsList.add(new Person(1, "John", 22, new Address(1, "Minsk", 220110)));
    }

    @Override
    @POST
    @Path("/create")
    public Response createPerson(Person person) {
        Response response = new Response();
        int newId = person.getId();
        personsList.forEach(person1 -> {
            if (person1.getId() == newId) {
                response.setStatus(false);
                response.setMsg("Person with id " + newId + " already exists!");

            }
        });

        if (response.getMsg() != null) {
            return response;
        }
        // add new person
        personsList.add(person);
        response.setStatus(true);
        response.setMsg("Person with id " + newId + " successfully added!");
        return response;
    }

    @Override
    @POST
    @Path("/update")
    public Response updatePerson(Person person) {
        Response response = new Response();
        if (getPerson(person.getId()) == null) {
            response.setStatus(false);
            response.setMsg("No user with ID " + person.getId() + " found!");
        } else {
            for (Person p : personsList) {
                if (p.getId() == person.getId()) {
                    p.setName(person.getName());
                    p.setAge(person.getAge());
                    response.setStatus(true);
                    response.setMsg("User with ID " + person.getId() + " updated!");
                }
            }
        }
        return response;
    }

    @Override
    @GET
    @Path("/get_all_by_age")
    public List<Person> getAllPersonsByAge(@QueryParam("from") int from, @QueryParam("to")int to) {
        ArrayList newList = new ArrayList<>();
        personsList.forEach(person -> {
            if (person.getAge() >= from &&person.getAge() <= to) {
                newList.add(person);
            }
        });
        return newList;
    }

    @Override
    @POST
    @Path("/update/{id}")
    public Response deletePerson(@PathParam("id") int id) {
        Response response = new Response();
        List<Person> personsToBeRemovedList = new ArrayList<>();
        personsToBeRemovedList.add(getPerson(id));
        if (personsToBeRemovedList.isEmpty()) {
            response.setStatus(false);
            response.setMsg("Person with id " + id + " not found!");
        } else {
            personsList.removeAll(personsToBeRemovedList);
            response.setStatus(true);
            response.setMsg("Person with id " + id + " successfully removed!");
        }
        return response;
    }

    @Override
    @GET
    @Path("/get/{id}")
    public Person getPerson(@PathParam("id") int id) {
        for (Person p : personsList) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    @GET
    @Path("/get_all")
    public List<Person> getAllPersons() {
        return personsList;
    }
}
