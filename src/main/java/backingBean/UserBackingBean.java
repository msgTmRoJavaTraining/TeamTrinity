package backingBean;


import entities.Role;
import entities.User;
import lombok.Data;
import org.primefaces.event.SelectEvent;
import validators.UserValidator;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "userBackingBean")
@SessionScoped
@Data
public class UserBackingBean implements Serializable {
    @EJB
    private DatabaseUserEJB userEJB;

    @EJB
    private DataGetter dataGetter;

    private int id;
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
        if (UserValidator.isValidEmail(email) && UserValidator.isValidPhoneNumber(phoneNumber)) {
            userEJB.createUser(firstName, lastName, email, phoneNumber, selectedRoles_Strings, password);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Date incorecte", "Email invalid"));
        }
    }

    public void updateUser() {
    }

    public void deleteUser() {
        userEJB.deleteUser(id);
    }

    public void readUser() {
        userEJB.readUser(firstName, lastName);
    }


    public void rowSelect(SelectEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("editUser.xhtml");
    }
}
