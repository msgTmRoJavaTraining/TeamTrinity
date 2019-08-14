package beans;

import backingBean.RightsManagementEJB;
import entities.Right;
import entities.Role;
import entities.User;
import security.WebHelper;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean(name = "rightsManagementBean")
@ApplicationScoped
public class RightsManagementBean {
    @Inject
    private RightsManagementEJB rightsManagementEJB;

    private List<Role> systemRoles;
    private List<Right> rolesRights;
    private List<Right> selectedRoleRights;
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
        selectedRoleRights = new ArrayList<>();
        selectedRoleRights = rightsList;
        boolean hasPermission = false;

        for(Right userRight : userRights) {
            if(userRight.getRightName().equals("PERMISSION_MANAGEMENT")) {
                hasPermission = true;
            }
        }

        if(hasPermission) {
            rightsManagementEJB.updateSelectedRoleRights(selectedRoleRights, updatedRole.getId());
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
