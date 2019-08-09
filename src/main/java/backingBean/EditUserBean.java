package backingBean;


import entities.User;
import lombok.Data;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

@Data
@ManagedBean(name = "editUserBean")
@ApplicationScoped
public class EditUserBean {

    @Inject
    private UserBackingBean userBackingBean;

    private User user=userBackingBean.getSelectedUser();

}
