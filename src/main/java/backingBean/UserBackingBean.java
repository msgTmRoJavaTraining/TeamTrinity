package backingBean;


import entities.Role;
import lombok.Data;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@ManagedBean(name = "userBackingBean")
@ApplicationScoped
@Data
public class UserBackingBean {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private List<Role> roles;
    private String password;

    @Inject
    private DatabaseUserEJB userEJB;


    public void createUser(){
        userEJB.createUser(firstName,lastName,phoneNumber,phoneNumber,roles,password);
    }

    public void updateUser(){}

    public void deleteUser(){
        userEJB.deleteUser(firstName,lastName);
    }

    public void readUser(){
        userEJB.readUser(firstName,lastName);
    }


}
