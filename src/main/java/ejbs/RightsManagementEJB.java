package ejbs;

import entities.Right;
import entities.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RightsManagementEJB {
    @PersistenceContext(name = "java.training")
    private EntityManager entityManager;

    public List<Role> getSystemRoles() {
        return entityManager.createQuery("select role from Role role", Role.class).getResultList();
    }

    public List<Right> getRolesRights() {
        return entityManager.createQuery("select right from Right right", Right.class).getResultList();
    }

    public void updateSelectedRoleRights(List<Right> selectedRights, int roleId) {
        Role role = entityManager.find(Role.class, roleId);
        List<Right> newRoleRights = selectedRights;

        role.setRights(newRoleRights);

        for(Right r : newRoleRights) {
            List<Role> existingRightRoles = r.getRolesRight();

            if (!existingRightRoles.contains(role)) {
                existingRightRoles.add(role);
            }
        }

        entityManager.merge(role);

        for(Right r : newRoleRights) {
            entityManager.merge(r);
        }
    }

    public void updateSystemRoles(List<Role> systemRoles) {
        for(Role role : systemRoles) {
            entityManager.merge(role);
        }
    }
}
