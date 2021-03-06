/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exceptions.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AssistantTeacher;
import model.Person;
import model.RoleSchool;
import model.Student;
import model.Teacher;

/**
 *
 * @author Marek FURAK
 */
public class PersonFacadeMock implements IPersonFacade

{

    List<Person> persons = new ArrayList();
    Gson gson = new Gson();
    long id = 0;

    @Override
    public String getPersonsAsJSON()
    {
        return gson.toJson(persons);
    }

    @Override
    public String getPersonAsJSON(long id) throws NotFoundException
    {
        if (id > persons.size())
        {
            throw new NotFoundException("No such ID in DB");
        }
        Person p = persons.get((int) id);
        return gson.toJson(p);

    }

    @Override
    public Person addPersonFromGson(String json)
    {
        Person p = gson.fromJson(json, Person.class);
        p.setId(id);
        id++;
        persons.add(p);
        return p;

    }

    @Override
    public RoleSchool addRoleSchoolFromGson(String json, long id)
    {
        JsonElement je = new JsonParser().parse(json);
        JsonObject jo = je.getAsJsonObject();
        JsonElement role = jo.get("roleName");
        String roleName = role.getAsString();
        RoleSchool r;
        switch (roleName)
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
        r.setPerson(persons.get((int) id));

        return r;
    }

    @Override
    public Person delete(long id) throws NotFoundException
    {
        if (id > persons.size())
        {
            throw new NotFoundException("No such ID in DB");
        }
        Person p = persons.get((int) id);
        persons.remove(p);
        return p;
    }

    @Override
    public Person editPersonFromGson(String json) throws NotFoundException
    {
        Person updatedPerson = gson.fromJson(json, Person.class);
        Long id = updatedPerson.getId();
        if (id > persons.size())
        {
            throw new NotFoundException("No such ID in DB");
        }
        Person existingPerson = persons.get(id.intValue());
        existingPerson.setFirstName(updatedPerson.getFirstName());
        existingPerson.setLastName(updatedPerson.getLastName());
        existingPerson.setPhone(updatedPerson.getPhone());
        existingPerson.setEmail(updatedPerson.getEmail());

        return existingPerson;
    }

    @Override
    public RoleSchool removeRoleSchoolFromGson(String json, long id)
    {
        RoleSchool rs = gson.fromJson(json, RoleSchool.class);

        Person p = null;
        try
        {
            p = gson.fromJson(getPersonAsJSON(id), Person.class);
        } catch (NotFoundException ex)
        {
            Logger.getLogger(PersonFacadeMock.class.getName()).log(Level.SEVERE, null, ex);
        }
        p.removeRole(rs);
        return rs;
    }

}
