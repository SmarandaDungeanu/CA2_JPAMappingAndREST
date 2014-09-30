/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author smarandadungeanu
 */
@Entity
public class Student extends RoleSchool implements Serializable {
    private static final long serialVersionUID = 1L;
    public String semester;

    public Student(String semester) {
        this.semester = semester;
    }

    public Student() {
    }

}
