package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.NotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Person;
import model.RoleSchool;
import static org.eclipse.persistence.sessions.SessionProfiler.Transaction;

public class PersonFacade implements IPersonFacade {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA2_JPAMappingAndRESTPU");
    EntityManager em = emf.createEntityManager();
    Map<Long, Person> persons = new HashMap();
    private AtomicLong nextId;
    private Gson gson = new Gson();
    private Gson gson1 = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public PersonFacade() {
        Query query = em.createNamedQuery("Person.findAll");
        Collection<Person> ps = query.getResultList();
        for (Person p : ps) {
            persons.put(p.getId(), p);
        }
    }

    @Override
    public String getPersonsAsJSON() {
        return gson1.toJson(persons);
    }

    @Override
    public String getPersonAsJSON(long id) throws NotFoundException {
        Person p = em.find(Person.class, id);
        if (p == null) {
            throw new NotFoundException("No person exists for the given id");
        }
        return gson1.toJson(p);
    }

    @Override
    public Person addPersonFromGson(String json) {
        Person p = gson.fromJson(json, Person.class);
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        persons.put(p.getId(), p);
        return p;
    }

    @Override
    public RoleSchool addRoleSchoolFromGson(String json, long id) {
        RoleSchool r = gson.fromJson(json, RoleSchool.class);
        r.setPerson(persons.get(id));
        em.getTransaction().begin();
        em.persist(r);
        em.getTransaction().commit();
        return r;
    }

    @Override
    public Person delete(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
