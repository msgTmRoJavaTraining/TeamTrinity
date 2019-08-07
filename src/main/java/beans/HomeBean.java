package beans;

import entities.Right;
import entities.Role;
import entities.User;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@ManagedBean(name = "homeBean")
@ApplicationScoped
public class HomeBean implements Serializable {
    @Inject
    private LoginBean loginBean;

    private boolean isAdmin = false;    //Facem sa dispara butonul de Rights Management
    private User loggedInUser = loginBean.getLoggedInUser();
    private List<Role> userRoles = loggedInUser.getRoles();

    @PostConstruct
    public void init() {
        System.out.println("USER NAME IS: " + loggedInUser.getName());
    }
}
