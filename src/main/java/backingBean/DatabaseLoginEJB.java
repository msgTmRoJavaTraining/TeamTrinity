package backingBean;

import entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;

@Stateless
public class DatabaseLoginEJB implements Serializable {
    @PersistenceContext(name = "java.training")
    private EntityManager entityManager;

    public int loginUserByUsernamePassword(String username, String password) {
        //OLD query:
        //select login.user from UserLogin login where login.username = :givenUsername and login.password = :givenPassword;
        TypedQuery<User> query = entityManager.createQuery("select user from User as user join UserLogin as login on user.userLogin.id = login.id where login.username = :givenUsername and login.password = :givenPassword", User.class);
        query.setParameter("givenUsername", username);
        query.setParameter("givenPassword", password);

        User foundUser = query.getSingleResult();
        return foundUser.getId();
    }
}
