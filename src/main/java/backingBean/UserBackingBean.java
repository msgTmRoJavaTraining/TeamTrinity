package backingBean;


import entities.Role;
import entities.User;
import lombok.Data;
import validators.UserValidator;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private List<User> userList;


    @Inject
    private DatabaseUserEJB userEJB;

    @Inject
    private DataGetter dataGetter;

    @PostConstruct
    public void init(){
        userList=dataGetter.getUsers();
    }

        public void createUser() {

        if (UserValidator.isValidEmail(email) && UserValidator.isValidPhoneNumber(phoneNumber)){
            userEJB.createUser(firstName, lastName, email, phoneNumber, roles, password);

    }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Date incorecte", "Email invalid"));

        }
    }


    public void updateUser(){}

    public void deleteUser(){
        userEJB.deleteUser(firstName,lastName);
    }

    public void readUser(){ userEJB.readUser(firstName,lastName); }


}
