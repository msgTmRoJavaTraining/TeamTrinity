package backingBean;


import Enums.StatusName;
import entities.Bug;
import entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DatabaseBugEJB {

    @PersistenceContext(unitName = "java.training")
    private EntityManager entityManager;

    public Bug createBug(String title, String description, String revision, String fixedInVersion,String createdBy,
                          String assignedTo) {

        Bug bug = new Bug();
        bug.setTitle(title);
        bug.setDescription(description);
        bug.setRevision(revision);
        bug.setFixedInVersion(fixedInVersion);
        bug.setStatus(StatusName.NEW);

        entityManager.persist(bug);

        return bug;
    }

    public void editBug(String name) {


    }

    public User readUser(String name){

        User user = entityManager.find(User.class,name);

        return user;
    }

    public User updateUser(){

        //will be completed wednesday 07.08.2019
        return null;
    }
}
