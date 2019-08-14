package backingBean;


import entities.Right;
import entities.Role;
import entities.User;
import lombok.Data;
import org.primefaces.event.SelectEvent;
import validators.UserValidator;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import javax.xml.bind.ValidationEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "userBackingBean")
@SessionScoped
@Data
public class UserBackingBean implements Serializable {
    @Inject
    private DatabaseUserEJB userEJB;

    @Inject
    private DataGetter dataGetter;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    private String email;
    private List<String> selectedRoles_Strings;
    private String password;

    private List<Role> roles;
    private List<Role> systemRoles;
    private List<Role> selectedRoles;
    private List<User> userList;
    private User selectedUser;
    private String message;

    @PostConstruct
    public void init() {
        systemRoles = new ArrayList<>();

        systemRoles = userEJB.getSystemRoles();
        userList = dataGetter.getUsers();

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
        userEJB.deleteUser(selectedUser.getUserLogin().getUsername());
    }

    public void readUser() {
        userEJB.readUser(selectedUser.getUserLogin().getUsername());
    }


    public void rowSelect() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("editUser.xhtml");
    }
}
