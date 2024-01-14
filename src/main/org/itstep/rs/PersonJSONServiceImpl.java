package main.org.itstep.rs;

import main.org.itstep.rs.dao.AddressDAO;
import main.org.itstep.rs.dao.PersonDAO;
import main.org.itstep.rs.dao.ResponseDAO;
import main.org.itstep.rs.model.Address;
import main.org.itstep.rs.model.Person;
import main.org.itstep.rs.model.Response;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/JSONPersons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonJSONServiceImpl implements PersonService {

    AddressDAO ad = new AddressDAO();
    PersonDAO pd = new PersonDAO();
    ResponseDAO rd = new ResponseDAO();

    private static List<Person> personsList = new ArrayList<>();

    @Override
    @POST
    @Path("/create")
    public Response createPerson(Person person) {
        Response response = new Response();
        int newId = person.getId();
        pd.findAll().forEach(person1 -> {
            if (person1.getId() == newId) {
                response.setStatus(false);
                response.setMsg("Person with id " + newId + " already exists!");

            }
        });

        if (response.getMsg() != null) {
            return response;
        }
        // add new person
        pd.create(person);
        response.setStatus(true);
        response.setMsg("Person with id " + newId + " successfully added!");
        return response;
    }

    @Override
    @POST
    @Path("/update")
    public Response updatePerson(Person person) {
        Response response = new Response();
        if (pd.findById(person.getId()) == null) {
            response.setStatus(false);
            response.setMsg("No user with ID " + person.getId() + " found!");
        } else {
            for (Person p : pd.findAll()) {
                if (p.getId() == person.getId()) {
                    p.setName(person.getName());
                    p.setAge(person.getAge());
                    p.setAddress(person.getAddress());
                    pd.update(p);
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
    public List<Person> getAllPersonsByAge(@QueryParam("from") int from, @QueryParam("to") int to) {
        List<Person> allList = pd.findAll();
        List<Person> newList = new ArrayList<>();
        allList.forEach(person -> {
            if (person.getAge() >= from && person.getAge() <= to) {
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
        if (pd.findById(id) == null) {
            response.setStatus(false);
            response.setMsg("Person with id " + id + " not found!");
        } else {
            pd.deleteById(id);
            response.setStatus(true);
            response.setMsg("Person with id " + id + " successfully removed!");
        }
        return response;
    }

    @Override
    @GET
    @Path("/get/{id}")
    public Person getPerson(@PathParam("id") int id) {
        return pd.findById(id);
    }

    @Override
    @GET
    @Path("/get_all")
    public List<Person> getAllPersons() {
        return pd.findAll();
    }
}
