package helpers;

import entities.Right;
import entities.Role;
import entities.User;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean
@SessionScoped
public class SecurityHelper implements Serializable {
    public boolean checkUserPermissions(String permission, User user) {
        List<Right> userRights = new ArrayList<>();

        // For each user role
        for (Role role : user.getRoles()) {
            //go through every right associated with
            userRights.addAll(role.getRights());
        }

        userRights = userRights.stream().distinct().collect(Collectors.toList());

        for (Right right : userRights) {
            if (permission.equals(right.getRightName())) {
                return true;
            }
        }

        return false;
    }
}
