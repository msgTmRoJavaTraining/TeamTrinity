package backingBean;

import entities.Role;
import entities.User;
import entities.UserLogin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Stateless
public class DatabaseUserEJB implements Serializable {

    @PersistenceContext(name = "java.training")
    private EntityManager entityManager;

    public void createUser(String firstName,String lastName, String email, String phoneNumber, List<Role> roles,String password) {

        User user = new User();
        UserLogin userLogin = new UserLogin();
        userLogin.setPassword(password);
        entityManager.persist(userLogin);
        user.setName(firstName + lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRoles(roles);
        user.setUserLogin(userLogin);
        entityManager.persist(user);

    }

    public void deleteUser(String firstName,String lastName) {

        User user = entityManager.find(User.class,firstName+lastName);
        entityManager.remove(user);

    }

    public User readUser(String firstName,String lastName){

        User user = entityManager.find(User.class,firstName+lastName);

        return user;
    }

    public User updateUser(){

        //will be completed wednesday 07.08.2019
        return null;
    }




}
