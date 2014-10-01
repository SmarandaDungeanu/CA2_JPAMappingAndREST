package model;

import javax.persistence.Entity;

@Entity
public class AssistantTeacher extends RoleSchool{
    private static final long serialVersionUID = 1L;

    public AssistantTeacher() {
        super.setRoleName("AssistantTeacher");
    }
    
}
