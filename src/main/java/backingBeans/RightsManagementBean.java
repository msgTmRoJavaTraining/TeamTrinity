package backingBeans;

import ejbs.RightsManagementEJB;
import entities.Right;
import entities.Role;
import entities.User;
import helpers.LanguagesBundleAccessor;
import helpers.NavigationHelper;
import helpers.SecurityHelper;
import security.WebHelper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "rightsManagementBean")
@ApplicationScoped
public class RightsManagementBean {
    @Inject
    private RightsManagementEJB rightsManagementEJB;

    @Inject
    private SecurityHelper securityHelper;

    @Inject
    private NavigationHelper navigationHelper;

    @Inject
    private LanguagesBundleAccessor languagesBundleAccessor;

    private List<Role> systemRoles;
    private List<Right> rolesRights;

    private User loggedInUser;

    @PostConstruct
    public void init() {
        loggedInUser = (User) WebHelper.getSession().getAttribute("loggedInUser");

        systemRoles = new ArrayList<>();
        rolesRights = new ArrayList<>();

        systemRoles = rightsManagementEJB.getSystemRoles();
        rolesRights = rightsManagementEJB.getRolesRights();
    }

    public void updateRoleRights() {
        if(securityHelper.checkUserPermissions("PERMISSION_MANAGEMENT", loggedInUser)) {
            rightsManagementEJB.updateSystemRoles(systemRoles);
            navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_INFO, languagesBundleAccessor.getResourceBundleValue("dialogMessage_rightsManagement_changeRights_success_title"), languagesBundleAccessor.getResourceBundleValue("dialogMessage_rightsManagement_changeRights_success_message"));
        } else {
            navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("dialogMessage_rightsManagement_changeRights_wrongPermission_title"), languagesBundleAccessor.getResourceBundleValue("dialogMessage_rightsManagement_changeRights_wrongPermission_message"));
            navigationHelper.customRedirectTo("homepage.xhtml");
        }
    }

    public List<Role> getSystemRoles() {
        return systemRoles;
    }

    public void setSystemRoles(List<Role> systemRoles) {
        this.systemRoles = systemRoles;
    }

    public List<Right> getRolesRights() {
        return rolesRights;
    }

    public void setRolesRights(List<Right> rolesRights) {
        this.rolesRights = rolesRights;
    }
}
