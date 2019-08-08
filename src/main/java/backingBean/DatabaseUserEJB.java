package backingBean;

import entities.Right;
import entities.Role;
import entities.User;
import entities.UserLogin;
import validators.HashingText;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class DatabaseUserEJB implements Serializable {

    @PersistenceContext(name = "java.training")
    private EntityManager entityManager;

    public List<Role> getSystemRoles() {
        return entityManager.createQuery("select role.roleName from Role role", Role.class).getResultList();
    }

    public Role getRoleByString(String roleName) {
        TypedQuery<Role> query = entityManager.createQuery("select role from Role role where role.roleName = :searchedForName", Role.class);
        query.setParameter("searchedForName", roleName);

        return query.getSingleResult();
    }

    //Verificare existenta utilizator
    public boolean checkAlreadyExistingUser(String searchedForEmail, String searchedForPhone) {
        TypedQuery<User> query = entityManager.createQuery("select user from User user where user.email = :mail and user.phoneNumber = :phone", User.class);
        query.setParameter("mail", searchedForEmail);
        query.setParameter("phone", searchedForPhone);

        return query.getResultList().size() == 0;
    }

    public boolean doesUsernameAlreadyExist(String generatedUserName) {
        TypedQuery<UserLogin> query = entityManager.createQuery("select data from UserLogin data where data.username like :givenUserName", UserLogin.class);
        query.setParameter("givenUserName", generatedUserName);

        return query.getResultList().size() <= 0;
    }

    public String generateUsername(String firstName, String lastName) {
        return "Hi there";
    }

    public boolean createUser(String firstName,String lastName, String email, String phoneNumber, List<String> selectedRoles_Strings,String password) {
        if (checkAlreadyExistingUser(email, phoneNumber)) {
            String username;

            List<Role> selectedRoles_Role = new ArrayList<>();
            for(String selectedRole : selectedRoles_Strings) {
                Role selectedRole_Role = getRoleByString(selectedRole);
                selectedRoles_Role.add(selectedRole_Role);
            }

            User user = new User();
            UserLogin userLogin = new UserLogin();
            userLogin.setPassword(HashingText.getMd5(password));

            user.setName(firstName + lastName);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setRoles(selectedRoles_Role);
            user.setActive(true);
            user.setUserLogin(userLogin);

            do {
                username = generateUsername(firstName, lastName);
            } while(!doesUsernameAlreadyExist(username));

            userLogin.setUsername(username);

            entityManager.persist(userLogin);
            entityManager.persist(user);

            return true;
        } else {
            return false;
        }
    }

    public void deleteUser(String firstName,String lastName) {

        User user = entityManager.find(User.class,firstName+lastName);
        user.setActive(false);

    }

    public User updateUser(){

        return null;
    }

    public User readUser(String firstName,String lastName){

        User user = entityManager.find(User.class,firstName+lastName);

        return user;

    }
}
