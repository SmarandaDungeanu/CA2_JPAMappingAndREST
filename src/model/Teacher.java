package model;

import com.google.gson.annotations.Expose;
import javax.persistence.Entity;

@Entity
public class Teacher extends RoleSchool{
    private static final long serialVersionUID = 1L;
    @Expose
    private String degree;

    public Teacher() {
    }

    public Teacher(String degree) {
        super.setRoleName("Teacher");
        this.degree = degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDegree() {
        return degree;
    }
}
