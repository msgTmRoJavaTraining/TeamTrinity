package beans;

import backingBean.RightsManagementEJB;
import entities.Right;
import entities.Role;
import entities.User;
import security.WebHelper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "rightsManagementBean")
@ApplicationScoped
public class RightsManagementBean {
    @Inject
    private RightsManagementEJB rightsManagementEJB;

    private List<Role> systemRoles;
    private List<Right> rolesRights;
    private List<Right> selectedRoleRights;

    @PostConstruct
    public void init() {
        systemRoles = new ArrayList<>();
        rolesRights = new ArrayList<>();

        systemRoles = rightsManagementEJB.getSystemRoles();
        rolesRights = rightsManagementEJB.getRolesRights();
    }

    public void onChangeSelectedRoleRights(List<Right> rightsList, Role updatedRole) {
        selectedRoleRights = new ArrayList<>();
        selectedRoleRights = rightsList;

        rightsManagementEJB.updateSelectedRoleRights(selectedRoleRights, updatedRole.getId());
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
