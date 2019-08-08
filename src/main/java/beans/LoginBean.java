package beans;

import entities.Right;
import entities.Role;
import entities.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "loginBean")
@ApplicationScoped
public class LoginBean implements Serializable {
    private String username;
    private String password;
//    private User loggedInUser = new User();
//
//    @PostConstruct
//    public void init() {
//        List<Right> rights = new ArrayList<>();
//        rights.add(new Right("RIGHT_MANAGEMENT"));
//
//        List<Role> roles = new ArrayList<>();
//        Role role = new Role();
//        role.setId(1);
//        role.setRights(rights);
//        role.setRoleName("ADMINISTRATOR");
//        role.setUser(null);
//
//        roles.add(role);
//
//        loggedInUser.setActive(false);
//        loggedInUser.setAssignedBuggs(null);
//        loggedInUser.setEmail("user@mail.msg.group.com");
//        loggedInUser.setId(1);
//        loggedInUser.setRoles(roles);
//        loggedInUser.setName("FirstName LastName");
//        loggedInUser.setNotifications(null);
//        loggedInUser.setPhoneNumber("12397234");
//        loggedInUser.setUserLogin(null);
//    }

    public String performLogin() {
        if (username.equals("username") && password.equals("password")) {
            return "homepage";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Date incorecte", "Email sau parola incorecte."));
            return "";
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    private String executeLogin(String username, String password) {
//        loggedInUser = loginEJB.login(username, password);
//        if(loggedInUser != null) {
//            if(loggedInUser.isActive() == false) {
//                loggedInUser.setActive(true);
//            }
//
//            return "homepage";
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login failed", "There was a problem with the login problem."));
//            return "";
//        }
//    }


//    public User getLoggedInUser() {
//        return loggedInUser;
//    }
    public String navigateTo(String page) {
        return page;
    }
}
