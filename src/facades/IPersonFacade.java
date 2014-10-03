package facades;

import exceptions.NotFoundException;
import model.Person;
import model.RoleSchool;

public interface IPersonFacade {
    public String getPersonsAsJSON();
    public String getPersonAsJSON(long id) throws NotFoundException;
    public Person addPersonFromGson(String json);
    public Person editPersonFromGson(String json) throws NotFoundException;
    public RoleSchool addRoleSchoolFromGson(String json, long id);
    public RoleSchool removeRoleSchoolFromGson(String json, long id); 
    public Person delete(long id) throws NotFoundException;
}
