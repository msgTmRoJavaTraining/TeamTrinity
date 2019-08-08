package backingBean;


import entities.Right;
import entities.Role;
import entities.User;
import lombok.Data;
import org.primefaces.context.RequestContext;
import validators.UserValidator;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.xml.bind.ValidationEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "userBackingBean")
@ApplicationScoped
@Data
public class UserBackingBean {
    @Inject
    private DatabaseUserEJB userEJB;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    private String email;
    private List<String> selectedRoles_Strings;
    private String password;

    private List<Role> roles;
    private List<Role> systemRoles;
    private List<Role> selectedRoles;

    @PostConstruct
    public void init() {
        systemRoles = new ArrayList<>();

        systemRoles = userEJB.getSystemRoles();
    }

    public void createUser() {
        if (UserValidator.areInputFieldsValid(firstName, lastName, email, phoneNumber, selectedRoles_Strings, password)) {
            if(userEJB.createUser(firstName, lastName, email, phoneNumber, selectedRoles_Strings, password)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"User added successfully", "Successfully added a new user in the system"));

                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("userManagement.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Could not add user", "The user already exists"));
            }
        }
    }

    public void updateUser() {
    }

    public void deleteUser() {
        userEJB.deleteUser(firstName, lastName);
    }

    public void readUser() {
        userEJB.readUser(firstName, lastName);
    }
}
