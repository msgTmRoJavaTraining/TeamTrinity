package beans;

import backingBean.RightsManagementEJB;
import entities.Right;
import entities.Role;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
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
        System.out.println("SE APELEAZA ON CHANGE");
        selectedRoleRights = new ArrayList<>();
        selectedRoleRights = rightsList;

        rightsManagementEJB.updateSelectedRoleRights(selectedRoleRights, updatedRole.getId());
    }

//    public String saveRolesRightsChanges(Object... objects){
//        for (Object o : objects) {
//            System.out.println(o.toString());
//        }
//        return "rightsManagement";
//    }

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
