/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exceptions.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import model.Person;
import model.RoleSchool;
import model.Student;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marek FURAK
 */
public class PersonFacadeTest
{

    PersonFacadeMock facade;
    Gson gson = new Gson();

    @Before
    public void setUp()
    {
        facade = new PersonFacadeMock();
    }

    /**
     * Test of getPersonsAsJSON method, of class PersonFacade.
     */
    @Test
    public void testGetPersonsAsJSON() throws NotFoundException
    {
        System.out.println("getPersonsAsJSON");
        List<Person> persons = gson.fromJson(facade.getPersonsAsJSON(), new TypeToken<List<Person>>()
        {
        }.getType());
        for (Person p : persons)
        {
            facade.delete(p.getId());
        }

        Person p1 = new Person("AAA", "BBB", "123", "AAA@BBB.CC");
        String personAsJson1 = gson.toJson(p1);
        p1 = facade.addPersonFromGson(personAsJson1);

        Person p2 = new Person("BBB", "CCC", "456", "BBB@CCC.DD");
        String personAsJson2 = gson.toJson(p2);
        p2 = facade.addPersonFromGson(personAsJson2);

        List<Person> list = new ArrayList();
        list.add(p1);
        list.add(p2);
        String expected = gson.toJson(list);
        String actual = facade.getPersonsAsJSON();
        assertEquals(expected, actual);

    }

    /**
     * Test of getPersonAsJSON method, of class PersonFacade.
     */
    @Test
    public void testGetPersonAsJSON() throws Exception
    {
        System.out.println("getPersonAsJSON");
        Person p = new Person("AAA", "BBB", "123", "AAA@BBB.CC");
        String personAsJson = gson.toJson(p);
        p = facade.addPersonFromGson(personAsJson);
        //update the JSON with the ID
        personAsJson = gson.toJson(p);
        String actual = facade.getPersonAsJSON(p.getId());
        assertEquals(personAsJson, actual);

    }

    /**
     * Test of addPersonFromGson method, of class PersonFacade.
     */
    @Test
    public void testAddPersonFromGson() throws Exception
    {
        System.out.println("addPersonFromGson");
        testGetPersonAsJSON();
    }

    /**
     * Test of addRoleSchoolFromGson method, of class PersonFacade.
     */
    @Test
    public void testAddRoleSchoolFromGson()
    {
        System.out.println("addRoleSchoolFromGson");
        List<Person> persons = gson.fromJson(facade.getPersonsAsJSON(), new TypeToken<List<Person>>()
        {
        }.getType());

        Person p1 = new Person("AAA", "BBB", "123", "AAA@BBB.CC");
        String personAsJson1 = gson.toJson(p1);
        p1 = facade.addPersonFromGson(personAsJson1);
        RoleSchool rs = new Student("Test");
        String role = gson.toJson(rs);

        persons.add(p1);

        rs = facade.addRoleSchoolFromGson(role, p1.getId());
        p1.addRole(rs);

        String expected = gson.toJson(persons);
        String actual = facade.getPersonsAsJSON();

        assertEquals(expected, actual);

    }

    /**
     * Test of delete method, of class PersonFacade.
     */
    @Test
    public void testDelete() throws Exception
    {
        System.out.println("delete");

        String expected = facade.getPersonsAsJSON();

        Person p1 = new Person("AAA", "BBB", "123", "AAA@BBB.CC");
        String personAsJson1 = gson.toJson(p1);
        p1 = facade.addPersonFromGson(personAsJson1);
        facade.delete(p1.getId());

        String actual = facade.getPersonsAsJSON();
        assertEquals(expected, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNonExisting() throws Exception
    {
        System.out.println("delete non existing");
        facade.delete(50000);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNonExistingPerson() throws Exception
    {
        System.out.println("get non existring error");
        facade.getPersonAsJSON(50000);
    }

    @Test
    public void testRemoveRoleSchool() throws NotFoundException
    {
        System.out.println("remove role");
        Person p1 = new Person("AAA", "BBB", "123", "AAA@BBB.CC");
        String personAsJson1 = gson.toJson(p1);
        p1 = facade.addPersonFromGson(personAsJson1);

        RoleSchool rs = new Student("Test");
        String role = gson.toJson(rs);

        rs = facade.addRoleSchoolFromGson(role, p1.getId());
        p1.addRole(rs);
        p1.removeRole(rs);
        role = gson.toJson(rs);
        facade.removeRoleSchoolFromGson(role, p1.getId());

        String expected = gson.toJson(p1);

        String actual = facade.getPersonAsJSON(p1.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void testEditPerson() throws NotFoundException
    {
        System.out.println("edit person");
        Person p1 = new Person("AAA", "BBB", "123", "AAA@BBB.CC");
        String personAsJson = gson.toJson(p1);
        p1 = facade.addPersonFromGson(personAsJson);
        p1.setFirstName("BBB");
        p1.setLastName("CCC");
        p1.setEmail("BBB@CCC.DD");
        p1.setPhone("6969");

        String expected = gson.toJson(p1);
        facade.editPersonFromGson(expected);

        String actual = facade.getPersonAsJSON(p1.getId());

        assertEquals(expected, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testEditNonExistingPerson() throws NotFoundException
    {
        System.out.println("edit person");
        Person p1 = new Person("AAA", "BBB", "123", "AAA@BBB.CC");
        String personAsJson = gson.toJson(p1);
        p1 = facade.addPersonFromGson(personAsJson);
        p1.setId(50000l);
        p1.setFirstName("BBB");
        p1.setLastName("CCC");
        p1.setEmail("BBB@CCC.DD");
        p1.setPhone("6969");

        String expected = gson.toJson(p1);
        facade.editPersonFromGson(expected);

//        String actual = facade.getPersonAsJSON(p1.getId());
//
//        assertEquals(expected, actual);
    }

    @Test(expected = NotFoundException.class)
    public void testRemoveNonExistingRoleSchool() throws NotFoundException
    {
        System.out.println("remove role");
        Person p1 = new Person("AAA", "BBB", "123", "AAA@BBB.CC");
        String personAsJson1 = gson.toJson(p1);
        p1 = facade.addPersonFromGson(personAsJson1);

        RoleSchool rs = new Student("Test");
        String role = gson.toJson(rs);
        rs = facade.addRoleSchoolFromGson(role, p1.getId());
        role = gson.toJson(rs);
        p1.setId(5000l);
        facade.removeRoleSchoolFromGson(role, p1.getId());
//
//        String expected = gson.toJson(p1);
//
//        String actual = facade.getPersonAsJSON(p1.getId());
//        assertEquals(expected, actual);
    }
}
