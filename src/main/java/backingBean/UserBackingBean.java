package backingBean;


import entities.Role;
import lombok.Data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
@Data
public class UserBackingBean {

    private String name;
    private String phoneNumber;
    private String email;
    private List<Role> roles;

    @Inject
    private DatabaseUserEJB userEJB;


    public void createUser(){
        userEJB.createUser(name,phoneNumber,phoneNumber,roles);
    }

    public void updateUser(){}

    public void deleteUser(){
        userEJB.deleteUser(name);
    }

    public void readUser(){
        userEJB.readUser(name);
    }


}
