package helpers;


import entities.Bug;
import entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Stateless
public class DataGetter implements Serializable {

    @PersistenceContext(name = "java.training")
    private EntityManager entityManager;

    public List<User> getUsers(){
        return entityManager.createQuery("select user from User user",User.class).getResultList();
    }

    public List<Bug> getBugs(){
        return entityManager.createQuery("select bug from Bug bug order by bug.targetData desc",Bug.class).getResultList();
    }

}
