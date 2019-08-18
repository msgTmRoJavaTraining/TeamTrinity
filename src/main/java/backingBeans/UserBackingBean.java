package backingBeans;


import ejbs.UserEJB;
import entities.Role;
import entities.User;
import helpers.DataGetter;
import helpers.LanguagesBundleAccessor;
import helpers.NavigationHelper;
import helpers.SecurityHelper;
import lombok.Data;
import org.primefaces.event.SelectEvent;
import security.WebHelper;
import validators.UserValidator;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@SessionScoped
@ManagedBean(name = "userBackingBean")
public class UserBackingBean implements Serializable {
    @Inject
    private UserValidator userValidator;

    @Inject
    private UserEJB userEJB;

    @Inject
    private DataGetter dataGetter;

    @Inject
    private NavigationHelper navigationHelper;

    @Inject
    private SecurityHelper securityHelper;

    @Inject
    private LanguagesBundleAccessor languagesBundleAccessor;

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

    private User loggedInUser;

    @PostConstruct
    public void init() {
        loggedInUser = (User) WebHelper.getSession().getAttribute("loggedInUser");

        systemRoles = new ArrayList<>();

        systemRoles = userEJB.getSystemRoles();
        userList = dataGetter.getUsers();

    }

    public void createUser() {
        if(securityHelper.checkUserPermissions("USER_MANAGEMENT", loggedInUser)) {
            if (userValidator.areInputFieldsValid(firstName, lastName, email, phoneNumber, selectedRoles_Strings, password)) {
                if (userEJB.createUser(firstName, lastName, email, phoneNumber, selectedRoles_Strings, password)) {
                    navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_INFO, languagesBundleAccessor.getResourceBundleValue("dialogMessage_userBackingBean_addUser_success_title"), languagesBundleAccessor.getResourceBundleValue("dialogMessage_userBackingBean_addUser_success_message"));
                    navigationHelper.customRedirectTo("userManagement.xhtml");
                } else {
                    navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("dialogMessage_userBackingBean_addUser_failure_title"), languagesBundleAccessor.getResourceBundleValue("dialogMessage_userBackingBean_addUser_failure_message"));
                }
            }
        } else {
            navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("dialogMessage_rightsManagement_changeRights_wrongPermission_title"), languagesBundleAccessor.getResourceBundleValue("dialogMessage_rightsManagement_changeRights_wrongPermission_message"));
            navigationHelper.customRedirectTo("homepage.xhtml");
        }
    }

    public void rowSelect(SelectEvent event) throws IOException {
        WebHelper.getSession().setAttribute("selectedUserForEdit", (User) event.getObject());
        FacesContext.getCurrentInstance().getExternalContext().redirect("editUser.xhtml");
    }
}
