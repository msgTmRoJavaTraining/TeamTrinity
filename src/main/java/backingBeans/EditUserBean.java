package backingBeans;


import ejbs.UserEJB;
import entities.Role;
import entities.User;
import helpers.LanguagesBundleAccessor;
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
    private UserEJB userEJB;

    @Inject
    private LanguagesBundleAccessor languagesBundleAccessor;

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
            if (userEJB.editUser(userId, email, phoneNumber, list)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, languagesBundleAccessor.getResourceBundleValue("dialogMessage_editUserBean_update_titleSuccess"), languagesBundleAccessor.getResourceBundleValue("dialogMessage_editUserBean_update_messageSuccess_1") + name + languagesBundleAccessor.getResourceBundleValue("dialogMessage_editUserBean_update_messageSuccess_2")));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, languagesBundleAccessor.getResourceBundleValue("dialogMessage_editUserBean_update_titleFailure"), languagesBundleAccessor.getResourceBundleValue("dialogMessage_editUserBean_update_messageFailure") + " " +  name));
            }
        }
    }

    public void changeAccountActivationStatus() {
        if(userEJB.changeAccountActivationStatus(userId)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, languagesBundleAccessor.getResourceBundleValue("dialogMessage_editUserBean_accountStatus_statusChange_title"), languagesBundleAccessor.getResourceBundleValue("dialogMessage_editUserBean_accountStatus_statusChange_message") + " " + username));
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("userManagement.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, languagesBundleAccessor.getResourceBundleValue("dialogMessage_editUserBean_accountStatus_statusChangeFailure_title"), languagesBundleAccessor.getResourceBundleValue("dialogMessage_editUserBean_accountStatus_statusChangeFailure_message") + " " + username));
        }
    }
}
