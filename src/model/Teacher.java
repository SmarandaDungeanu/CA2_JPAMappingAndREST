/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Teacher extends RoleSchool implements Serializable {
    private static final long serialVersionUID = 1L;
    public String degree;

    public Teacher() {
    }

    public Teacher(String degree) {
        this.degree = degree;
    }
}
