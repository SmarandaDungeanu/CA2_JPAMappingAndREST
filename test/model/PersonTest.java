/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
public class PersonTest
{

    Person person;

    public PersonTest()
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
        person = new Person("FirstName", "LastName", "1234", "test@gmail.com");
    }

    @After
    public void tearDown()
    {
    }

//    /**
//     * Test of addRole method, of class Person.
//     */
//    @Test
//    public void testAddRole()
//    {
//        System.out.println("addRole");
//        Student s = new Student("1");
//        person.addRole(s);
//    }

    /**
     * Test of getId method, of class Person.
     */
    @Test
    public void testGetId()
    {
        System.out.println("getId");
        long id = 1;
        person.setId(id);
        long result = person.getId();
        assertEquals(id, result);
    }

    /**
     * Test of setId method, of class Person.
     */
    @Test
    public void testSetId()
    {
        System.out.println("setId");
        long id = 1;
        person.setId(id);
        long result = person.getId();
        assertEquals(id, result);
    }

    /**
     * Test of setLastName method, of class Person.
     */
    @Test
    public void testSetLastName()
    {
        System.out.println("setLastName");
        String name = "Lname";
        person.setLastName(name);
        String result = person.getLastName();
        assertEquals(name, result);
    }

    /**
     * Test of setFirstName method, of class Person.
     */
    @Test
    public void testSetFirstName()
    {
        System.out.println("setFirstName");
        String name = "Fname";
        person.setLastName(name);
        String result = person.getLastName();
        assertEquals(name, result);
    }

    /**
     * Test of getLastName method, of class Person.
     */
    @Test
    public void testGetLastName()
    {
        System.out.println("getLastName");
        String name = "LastName";
        String result = person.getLastName();
        assertEquals(name, result);
    }

    /**
     * Test of getFirstName method, of class Person.
     */
    @Test
    public void testGetFirstName()
    {
        System.out.println("getFirstName");
        String name = "FirstName";
        String result = person.getFirstName();
        assertEquals(name, result);
    }

    /**
     * Test of setPhone method, of class Person.
     */
    @Test
    public void testSetPhone()
    {
        System.out.println("setPhone");
        String phone = "0000";
        person.setPhone(phone);
        String result = person.getPhone();
        assertEquals(phone, result);
    }

    /**
     * Test of setEmail method, of class Person.
     */
    @Test
    public void testSetEmail()
    {
        System.out.println("setEmail");
        String email = "name@test.com";
        person.setEmail(email);
        String result = person.getEmail();
        assertEquals(email, result);
    }

    /**
     * Test of getPhone method, of class Person.
     */
    @Test
    public void testGetPhone()
    {
        System.out.println("getPhone");
        String phone = "1234";
        String result = person.getPhone();
        assertEquals(phone, result);
    }

    /**
     * Test of getEmail method, of class Person.
     */
    @Test
    public void testGetEmail()
    {
        System.out.println("getEmail");

        String email = "test@gmail.com";
        String result = person.getEmail();
        assertEquals(email, result);
    }
}
