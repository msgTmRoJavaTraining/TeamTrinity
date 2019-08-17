package backingBeans;

import ejbs.RightsManagementEJB;
import entities.Right;
import entities.Role;
import entities.User;
import helpers.SecurityHelper;
import security.WebHelper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ManagedBean(name = "rightsManagementBean")
@ApplicationScoped
public class RightsManagementBean {
    @Inject
    private RightsManagementEJB rightsManagementEJB;

    @Inject
    private SecurityHelper securityHelper;

    private List<Role> systemRoles;
    private List<Right> rolesRights;
    private List<Right> selectedRoleRights = new ArrayList<>();
    private List<Role> selectedRolesForUpdate = new ArrayList<>();
    private List<Right> userRights;

    private User loggedInUSer;

    @PostConstruct
    public void init() {
        loggedInUSer = (User) WebHelper.getSession().getAttribute("loggedInUser");

        userRights = new ArrayList<>();
        // For each user role
        for (Role role : loggedInUSer.getRoles()) {
            //go through every right associated with
            userRights.addAll(role.getRights());
        }

        userRights = userRights.stream().distinct().collect(Collectors.toList());

        systemRoles = new ArrayList<>();
        rolesRights = new ArrayList<>();

        systemRoles = rightsManagementEJB.getSystemRoles();
        rolesRights = rightsManagementEJB.getRolesRights();
    }

    public void onChangeSelectedRoleRights(List<Right> rightsList, Role updatedRole) {
        selectedRoleRights = rightsList;

        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        if(securityHelper.checkUserPermissions("PERMISSION_MANAGEMENT", userRights)) {
            rightsManagementEJB.updateSelectedRoleRights(selectedRoleRights, updatedRole.getId());
           context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation successful", "Successfully updated role rights"));
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "NU", "Fuck off"));

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("homepage.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateRoleRights() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        context.getExternalContext().getFlash().setKeepMessages(true);

        if(securityHelper.checkUserPermissions("PERMISSION_MANAGEMENT", userRights)) {
            rightsManagementEJB.updateSystemRoles(systemRoles);
            securityHelper.showGrowlMessage(FacesMessage.SEVERITY_INFO, "Operation successful", "Successfully updated role rights");

            securityHelper.customRedirectTo("homepage.xhtml");
        } else {
            securityHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, "NU", "Fuck off");

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("homepage.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
