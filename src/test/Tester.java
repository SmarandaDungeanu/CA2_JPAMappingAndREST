package test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Person;
import model.RoleSchool;
import model.Student;

public class Tester {
    
    private EntityManager createEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CA2_JPAMappingAndRESTPU");
        return emf.createEntityManager();
    }
    public void test(){
        EntityManager em = createEntityManager();
        Person p1 = new Person("Smaranda", "Dungeanu", "50223347", "smaranda.dungeanu@gmail.com");
        RoleSchool r1 = new RoleSchool("hey");
        Student s1 = new Student("sem1");
        p1.addRole(r1);
        p1.addRole(s1);
        
        em.getTransaction().begin();
        em.persist(p1);
        em.persist(r1);
        em.persist(s1);
        em.getTransaction().commit();
        em.close();
    }
    
    public static void main(String[] args) {
        new Tester().test();
    }
}
