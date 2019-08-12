package backingBean;


import entities.Role;
import entities.User;
import lombok.Data;
import security.WebHelper;
import validators.UserValidator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ManagedBean(name = "editUserBean")
@SessionScoped
public class EditUserBean implements Serializable {
    private User selectedUserForEdit;

//    @Inject
//    private UserBackingBean userBackingBean;

    @Inject
    private DatabaseUserEJB databaseUserEJB;

    private String phoneNumber;
    private String email;
    private String lastName;
    private String firstName;
    private String username;
    private int userId;
    private List<Role> userRoles;
    private List<String> userSelectedRoles;

    @PostConstruct
    public void afterStart() {
        selectedUserForEdit = (User) WebHelper.getSession().getAttribute("selectedUserForEdit");

        phoneNumber = selectedUserForEdit.getPhoneNumber();
        email = selectedUserForEdit.getEmail();
        userRoles = selectedUserForEdit.getRoles();
        userId = selectedUserForEdit.getId();

        String[] nameParts = selectedUserForEdit.getName().split(" ");
        lastName = nameParts[0];
        firstName = nameParts[1];

        userSelectedRoles = userRoles.stream().map(Role::getRoleName).collect(Collectors.toList());

        username = selectedUserForEdit.getUserLogin().getUsername();
    }

    @PreDestroy
    public void clearSession() {
        WebHelper.getSession().setAttribute("selectedUserForEdit", null);
    }

    public void editUser(){
//        if(UserValidator.userUpdateValidFields(firstName, lastName, email, phoneNumber, userRoles)) {
//            if (databaseUserEJB.editUser(userId, firstName, lastName, email, phoneNumber, userRoles)) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User updated", "User " + lastName + " " + firstName + " has been updated!"));
//            } else {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "User update failed", "Something went wrong while updating " + lastName + " " + firstName));
//            }
//        }
    }

    public void deactivateUser() {
//        if(databaseUserEJB.deactivateUser(userId)) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account deactivated", "Successfully deactivated " + username));
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Account deactivation failed", "There was a problem deactivating " + username));
//        }
    }
}
