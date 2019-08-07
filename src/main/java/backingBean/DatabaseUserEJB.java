package backingBean;

import entities.Role;
import entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Data
@Named
@ApplicationScoped
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseUserEJB implements Serializable {

    @PersistenceContext(unitName = "java.training")
    private EntityManager entityManager;

    public User createUser(String name, String email, String phoneNumber, List<Role> roles) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRoles(roles);
        entityManager.persist(user);

        return user;
    }

    public void deleteUser(String name) {

        User user = entityManager.find(User.class,name);
        entityManager.remove(user);

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
