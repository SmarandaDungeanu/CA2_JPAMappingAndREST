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
public class PersonFacadeIntegrationTest
{

    PersonFacade facade;
    Gson gson = new Gson();

    public PersonFacadeIntegrationTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
        facade = new PersonFacade();
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of getPersonsAsJSON method, of class PersonFacade.
     */
    @Test
    public void testGetPersonsAsJSON() throws NotFoundException
    {
        System.out.println("getPersonsAsJSON");

        for (Person p : facade.persons)
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
        List<Person> persons = gson.fromJson(facade.getPersonsAsJSON(), new TypeToken<List<Person>>()
        {
        }.getType());
        System.out.println("addRoleSchoolFromGson");
        Person p1 = new Person("AAA", "BBB", "123", "AAA@BBB.CC");
        String personAsJson1 = gson.toJson(p1);
        p1 = facade.addPersonFromGson(personAsJson1);
        RoleSchool rs = new Student("Test");
        String role = gson.toJson(rs);

        persons.add(p1);
        p1.addRole(rs);
        rs = facade.addRoleSchoolFromGson(role, p1.getId());

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
}
