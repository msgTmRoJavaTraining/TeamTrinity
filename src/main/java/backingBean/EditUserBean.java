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
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ManagedBean(name = "editUserBean")
@SessionScoped

public class EditUserBean implements Serializable {
    private User selectedUserForEdit;

    @Inject
    private UserBackingBean userBackingBean;

    @Inject
    private DatabaseUserEJB databaseUserEJB;

    private String phoneNumber;
    private String email;
    private String username;
    private String name;
    private int userId;
    private List<Role> userRoles;
    private List<Role> systemRoles;
    private List<String> userSelectedRoles;

    private String accountActivationStatus;

    @PostConstruct
    public void afterStart() {
        selectedUserForEdit = (User) WebHelper.getSession().getAttribute("selectedUserForEdit");

        phoneNumber = selectedUserForEdit.getPhoneNumber();
        email = selectedUserForEdit.getEmail();
        name = selectedUserForEdit.getName();
        userRoles = selectedUserForEdit.getRoles();
        userId = selectedUserForEdit.getId();

        systemRoles = userBackingBean.getSystemRoles();

        userSelectedRoles = userRoles.stream().map(Role::getRoleName).collect(Collectors.toList());

        username = selectedUserForEdit.getUserLogin().getUsername();

        if(selectedUserForEdit.getAccountActiveStatus()) {
            accountActivationStatus = "Deactivate account";
        } else {
            accountActivationStatus = "Activate account";
        }
    }

    @PreDestroy
    public void clearSession() {
        WebHelper.getSession().setAttribute("selectedUserForEdit", null);
    }

    public void editUser(List<String> list){
        if(UserValidator.userUpdateValidFields(email, phoneNumber, list)) {
            if (databaseUserEJB.editUser(userId, email, phoneNumber, list)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User updated", "User " + name + " has been updated!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "User update failed", "Something went wrong while updating " + name));
            }
        }
    }

    public void changeAccountActivationStatus() {
        if(databaseUserEJB.changeAccountActivationStatus(userId)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Account deactivated", "Successfully deactivated " + username));
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("userManagement.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Account deactivation failed", "There was a problem deactivating " + username));
        }
    }
}
