package backingBean;

import entities.Role;
import entities.User;
import entities.UserLogin;
import validators.HashingText;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class DatabaseUserEJB implements Serializable {

    @PersistenceContext(name = "java.training")
    private EntityManager entityManager;


    public User createUser(String firstName,String lastName, String email, String phoneNumber, List<Role> roles,String password) {
        User user = new User();
        UserLogin userLogin = new UserLogin();
        userLogin.setPassword(HashingText.getMd5(password));
        entityManager.persist(userLogin);
        user.setName(firstName + lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRoles(roles);
        user.setActive(true);
        user.setUserLogin(userLogin);
        entityManager.persist(user);
        return user;
    }


    public void deleteUser(String firstName,String lastName) {

        User user = entityManager.find(User.class,firstName+lastName);
        user.setActive(false);

    }

    public User readUser(String firstName,String lastName){

        User user = entityManager.find(User.class,firstName+lastName);

        return user;

    }

    public User updateUser(){

        //will be completed wednesday 08.08.2019
        return null;
    }




}
