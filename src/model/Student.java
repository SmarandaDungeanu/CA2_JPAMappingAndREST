/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.annotations.Expose;
import javax.persistence.Entity;

/**
 *
 * @author smarandadungeanu
 */
@Entity
public class Student extends RoleSchool {
    private static final long serialVersionUID = 1L;
    @Expose
    private String semester;

    public Student(String semester) {
        super.setRoleName("Student");
        this.semester = semester;
    }

    public Student() {
    }

    public void setSemester(String roleName, String semester) {
        this.semester = semester;
    }

    @Override
    public String getRoleName() {
        return super.getRoleName(); //To change body of generated methods, choose Tools | Templates.
    }


    public String getSemester() {
        return semester;
    }

}
