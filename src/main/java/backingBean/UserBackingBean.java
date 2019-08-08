package backingBean;


import entities.Right;
import entities.Role;
import entities.User;
import lombok.Data;
import org.primefaces.event.SelectEvent;
import validators.UserValidator;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "userBackingBean")
@ApplicationScoped
@Data
public class UserBackingBean {
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
        if (UserValidator.isValidEmail(email) && UserValidator.isValidPhoneNumber(phoneNumber)) {
            userEJB.createUser(firstName, lastName, email, phoneNumber, selectedRoles_Strings, password);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Date incorecte", "Email invalid"));
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


    public void rowSelect(SelectEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("editUser.xhtml");
    }
}
