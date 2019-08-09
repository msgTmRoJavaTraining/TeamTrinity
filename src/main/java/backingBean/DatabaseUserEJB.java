package backingBean;

import entities.Role;
import entities.User;
import entities.UserLogin;
import validators.HashingText;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class DatabaseUserEJB implements Serializable {
    private int lastNameIndex = 0, firstNameIndex = 0;

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

    public String generateUsername(String firstName, String lastName, int numberOfMethodCalls) {
        int MAX_USERNAME_LENGTH = 6, i;
        StringBuilder generatedUserName = new StringBuilder();
        char[] firstNameArray = firstName.toCharArray();
        char[] lastNameArray = lastName.toCharArray();
        int firstNameLength = firstNameArray.length;
        int lastNameLength = lastNameArray.length;

        //If the last name has less than 5 characters
        if (lastNameLength < 5) {
            //pornim de la 0
            if (numberOfMethodCalls == 0) {
                firstNameIndex = 0;
                lastNameIndex = 0;
            } else {
                //daca LNI este 0, atunci incrementam pana ajungem la ultimul caracter
                if (lastNameIndex >= 0 && lastNameIndex < lastNameLength) {
                    lastNameIndex++;
                } else if (lastNameIndex >= 0 && firstNameIndex < firstNameLength) {
                    firstNameIndex++;
                }
            }
            //place all the LN characters in the final string
            for (i = 0; i < lastNameLength - lastNameIndex; i++) {
                generatedUserName.append(lastNameArray[i]);
            }

            //place FN characters until we have a maximum of 6 characters in the final string
            for (i = firstNameIndex; i < firstNameLength && generatedUserName.length() < MAX_USERNAME_LENGTH; i++) {
                generatedUserName.append(firstNameArray[i]);
            }
            //If the last name has at least 5 characters
        } else {
            //When we call this method for the first time, the indexes start with 0
            if (numberOfMethodCalls == 0) {
                firstNameIndex = 0;
                lastNameIndex = 0;
            } else {
                //if we still have characters to try from the first name
                if (firstNameIndex < firstNameLength - 1) {
                    firstNameIndex++;
                    //if we finished traversing the first name, we need to decrement the last name and start over with the first name traversal
                } else {
                    //this only runs once, because the above condition becomes valid again
                    firstNameIndex = 0;
                    lastNameIndex++;
                }
            }

            //place a maximum of 5 characters from LN
            for (i = 0; i < 5 - lastNameIndex && generatedUserName.length() < 5; i++) {
                generatedUserName.append(lastNameArray[i]);
            }

            //place a character from FN until the final string has a length of 6 chars
            for (i = firstNameIndex; i < firstNameLength && generatedUserName.length() < MAX_USERNAME_LENGTH; i++) {
                generatedUserName.append(firstNameArray[i]);
            }
        }

        //If the final generated username still has less than 6 characters, we'll repeat adding the first character from the first name
        while (generatedUserName.length() < 6) {
            generatedUserName.append(firstNameArray[0]);
        }

        return generatedUserName.toString();
    }

    public boolean createUser(String firstName, String lastName, String email, String phoneNumber, List<String> selectedRoles_Strings, String password) {
        int generateUserNameCalledTimes = 0;
        if (checkAlreadyExistingUser(email, phoneNumber)) {
            String username;

            List<Role> selectedRoles_Role = new ArrayList<>();
            for (String selectedRole : selectedRoles_Strings) {
                Role selectedRole_Role = getRoleByString(selectedRole);
                selectedRoles_Role.add(selectedRole_Role);
            }

            User user = new User();
            UserLogin userLogin = new UserLogin();
            userLogin.setPassword(HashingText.getMd5(password));

            user.setName(firstName + " " + lastName);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setRoles(selectedRoles_Role);
            user.setActive(true);
            user.setUserLogin(userLogin);

            do {
                username = generateUsername(firstName.toLowerCase(), lastName.toLowerCase(), generateUserNameCalledTimes++);
            } while (!doesUsernameAlreadyExist(username));

            userLogin.setUsername(username);

            entityManager.persist(userLogin);
            entityManager.persist(user);

            return true;
        } else {
            return false;
        }
    }

    public void deleteUser(String firstName, String lastName) {

        User user = entityManager.find(User.class, firstName + lastName);
        user.setActive(false);

    }

    public User updateUser() {

        return null;
    }

    public User readUser(String firstName, String lastName) {
        User user = entityManager.find(User.class, firstName + lastName);

        return user;
    }
}
