package backingBean;


import entities.Role;
import entities.User;
import lombok.Data;
import security.WebHelper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ManagedBean(name = "editUserBean")
@SessionScoped
public class EditUserBean implements Serializable {
    private User selectedUserForEdit = (User) WebHelper.getSession().getAttribute("selectedUserForEdit");

    @Inject
    private UserBackingBean userBackingBean;

    @Inject
    private DatabaseUserEJB databaseUserEJB;

    private String phoneNumber;
    private String email;
    private String name;
    private String username;
    private List<Role> userRoles;
    private List<String> userSelectedRoles;

    @PostConstruct
    public void afterStart() {
        WebHelper.getSession().setAttribute("selectedUserForEdit", null);

        phoneNumber = selectedUserForEdit.getPhoneNumber();
        email = selectedUserForEdit.getEmail();
        name = selectedUserForEdit.getName();
        userRoles = selectedUserForEdit.getRoles();

        userSelectedRoles = userRoles.stream().map(Role::getRoleName).collect(Collectors.toList());

        username = selectedUserForEdit.getUserLogin().getUsername();
    }

    public void editUser(){
        User user=userBackingBean.getSelectedUser();
        databaseUserEJB.editUser(user,phoneNumber,email);
    }

}
