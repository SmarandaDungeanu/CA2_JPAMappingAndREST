package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.NotFoundException;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.AssistantTeacher;
import model.Person;
import model.RoleSchool;
import model.Student;
import model.Teacher;

public class PersonFacade implements IPersonFacade
{

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA2_JPAMappingAndRESTPU");
    private EntityManager em;
    private Gson gson = new Gson();
    private Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public PersonFacade()
    {
    }

    @Override
    public String getPersonsAsJSON()
    {
        em = emf.createEntityManager();
        Query query = em.createNamedQuery("Person.findAll");
        Collection<Person> ps = query.getResultList();
        return gson1.toJson(ps);
    }

    @Override
    public String getPersonAsJSON(long id) throws NotFoundException
    {
        em = emf.createEntityManager();
        Person p = em.find(Person.class, id);
        if (p == null)
        {
            throw new NotFoundException("No person exists for the given id");
        }
        return gson1.toJson(p);
    }

    @Override
    public Person addPersonFromGson(String json)
    {
        Person p = gson.fromJson(json, Person.class);
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        return p;
    }

    @Override
    public Person editPersonFromGson(String json) throws NotFoundException
    {
        em = emf.createEntityManager();
        Person updatedPerson = gson.fromJson(json, Person.class);
        em.getTransaction().begin();
        Person existingPerson = em.find(Person.class, updatedPerson.getId());
        if (existingPerson == null)
        {
            throw new NotFoundException("No person exists for the given id");
        }
        existingPerson.setFirstName(updatedPerson.getFirstName());
        existingPerson.setLastName(updatedPerson.getLastName());
        existingPerson.setPhone(updatedPerson.getPhone());
        existingPerson.setEmail(updatedPerson.getEmail());
        em.getTransaction().commit();
        return existingPerson;
    }

    @Override
    public RoleSchool addRoleSchoolFromGson(String json, long id)
    {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        Person p = em.find(Person.class, id);

        RoleSchool r;
        switch (gson.fromJson(json, RoleSchool.class).getRoleName())
        {
            case "Student":
                r = gson.fromJson(json, Student.class);
                break;
            case "Teacher":
                r = gson.fromJson(json, Teacher.class);
                break;
            case "AssistantTeacher":
                r = gson.fromJson(json, AssistantTeacher.class);
                break;
            default:
                r = gson.fromJson(json, RoleSchool.class);
                break;
        }
        r.setPerson(p);
        em.persist(r);
        em.getTransaction().commit();
        return r;
    }

    @Override
    public RoleSchool removeRoleSchoolFromGson(String json, long id)
    {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        RoleSchool rsFromDB = em.find(RoleSchool.class, gson.fromJson(json, RoleSchool.class).getId());
        em.remove(rsFromDB);

        Person p = em.find(Person.class, id);
        p.removeRole(rsFromDB);
        em.persist(p);
        em.getTransaction().commit();
        return rsFromDB;
    }

    @Override
    public Person delete(long id) throws NotFoundException
    {
        em = emf.createEntityManager();
        Person p = em.find(Person.class, id);
        if (p == null)
        {
            throw new NotFoundException("No person exists for the given id");
        }
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
        return p;
    }

}
