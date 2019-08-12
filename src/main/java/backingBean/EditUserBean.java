package backingBean;


import entities.User;
import lombok.Data;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Data
@ManagedBean(name = "editUserBean")
@SessionScoped

public class EditUserBean implements Serializable {

    @Inject
    private UserBackingBean userBackingBean;

    @Inject
    private DatabaseUserEJB databaseUserEJB;


    private String phoneNumber;
    private String email;

    public void editUser(){
        User user=userBackingBean.getSelectedUser();
        databaseUserEJB.editUser(user,phoneNumber,email);
    }

}
