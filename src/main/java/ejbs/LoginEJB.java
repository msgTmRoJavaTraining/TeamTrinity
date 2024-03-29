package ejbs;

import entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;

@Stateless
public class LoginEJB implements Serializable {
    @PersistenceContext(name = "java.training")
    private EntityManager entityManager;

    public User doesAccountExist(String username) {
        TypedQuery<User> query = entityManager.createQuery("select user from User as user join UserLogin as login on user.userLogin.id = login.id where login.username = :givenUsername", User.class);
        query.setParameter("givenUsername", username);

        return query.getSingleResult();
    }

    public User loginUserByUsernamePassword(String username, String password) {
        TypedQuery<User> query = entityManager.createQuery("select user from User as user join UserLogin as login on user.userLogin.id = login.id where login.username = :givenUsername and login.password = :givenPassword", User.class);
        query.setParameter("givenUsername", username);
        query.setParameter("givenPassword", password);

        User foundUser = query.getSingleResult();
        return foundUser;
    }

    public void deactivateAccount(User toBeDeactivatedAccount) {
        toBeDeactivatedAccount.setActive(false);

        try {
            entityManager.merge(toBeDeactivatedAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
