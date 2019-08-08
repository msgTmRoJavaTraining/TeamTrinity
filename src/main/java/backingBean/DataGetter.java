package backingBean;


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


}
