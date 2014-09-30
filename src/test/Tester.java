package test;

import com.google.gson.Gson;
import exceptions.NotFoundException;
import facades.PersonFacade;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Person;
import model.RoleSchool;
import model.Student;

public class Tester {
    
    PersonFacade fac = new PersonFacade();
    private EntityManager createEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA2_JPAMappingAndRESTPU");
        
        return emf.createEntityManager();
    }
    public void test() throws NotFoundException{
        Gson gson = new Gson();
        EntityManager em = createEntityManager();
        Person p1 = new Person("Smaranda", "Dungeanu", "50223347", "smaranda.dungeanu@gmail.com");
        RoleSchool r1 = new RoleSchool("hey");
        Student s1 = new Student("sem1");
        long id = fac.addPersonFromGson(gson.toJson(p1)).getId();
        fac.addRoleSchoolFromGson(gson.toJson(r1),id);
        fac.addRoleSchoolFromGson(gson.toJson(s1), id);
        System.out.println(fac.getPersonAsJSON(id));
    }
    
    public static void main(String[] args) throws NotFoundException {
        new Tester().test();
    }
}
