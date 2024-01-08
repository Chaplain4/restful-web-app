package main.org.itstep.rs;

import main.org.itstep.rs.model.Person;
import main.org.itstep.rs.model.Response;

import java.util.List;

public interface PersonService {
    public Response createPerson(Person person);
    public Response deletePerson(int id);
    public Person getPerson(int id);
    public List<Person> getAllPersons();
}
