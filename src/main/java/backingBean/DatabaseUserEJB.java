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

    //Pentru update roluri ...
//    public void updateSelectedRoleRights(List<Role> selectedUserRoles, int userId) {
//        User user = entityManager.find(User.class, userId);
//        List<Role> newUserRoles = selectedUserRoles;
//
//        user.setRoles(newUserRoles);
//
//        for(Role r : newUserRoles) {
//            List<User> existingUserRoles = r.getUser();
//
//            if (!existingUserRoles.contains(user)) {
//                existingUserRoles.add(user);
//            }
//        }
//
//        entityManager.merge(user);
//
//        for(Role r : newUserRoles) {
//            entityManager.merge(r);
//        }
//    }

    public void createUser(String firstName,String lastName, String email, String phoneNumber, List<String> selectedRoles_Strings,String password) {
        List<Role> selectedRoles_Role = new ArrayList<>();
        for(String selectedRole : selectedRoles_Strings) {
            Role selectedRole_Role = getRoleByString(selectedRole);
            selectedRoles_Role.add(selectedRole_Role);
        }

        User user = new User();
        UserLogin userLogin = new UserLogin();
        userLogin.setPassword(HashingText.getMd5(password));
        entityManager.persist(userLogin);
        user.setName(firstName + lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRoles(selectedRoles_Role);
        user.setActive(true);
        user.setUserLogin(userLogin);
        entityManager.persist(user);

    }

    public void deleteUser(int id) {

        User user = entityManager.find(User.class,id);
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
