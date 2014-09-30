package model;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class AssistantTeacher extends RoleSchool implements Serializable {
    private static final long serialVersionUID = 1L;

    public AssistantTeacher() {
        super.setRoleName("AssistantTeacher");
    }
    
}
