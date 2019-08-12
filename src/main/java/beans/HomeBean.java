package beans;

import entities.Right;
import entities.Role;
import entities.User;
import lombok.Data;
import security.WebHelper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ManagedBean(name = "homeBean")
@ApplicationScoped
public class HomeBean implements Serializable {
    private boolean rightsManagement = false;
    private boolean userManagement = false;
    private boolean bugManagement = false;
    private boolean notifications = true;

    List<Role> userRoles;
    List<Right> userRightsBasedOnRoles = new ArrayList<>();

    private User loggerInUser = (User) WebHelper.getSession().getAttribute("loggedInUser");
    private String welcomeMessage = "Welcome to JBugger " + loggerInUser.getName() + ", let the fun begin!";

    @PostConstruct
    public void init() {
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfulyl logged in!", "Welcome " + loggerInUser.getName()));
        userRoles = loggerInUser.getRoles();

        List<Right> temporaryRightsList = new ArrayList<>();
        // For each user role
        for (Role role : userRoles) {
            //go through every right associated with
            temporaryRightsList.addAll(role.getRights());
        }

        userRightsBasedOnRoles = temporaryRightsList.stream().distinct().collect(Collectors.toList());

        enableApplicationModulesByRights(userRightsBasedOnRoles);
    }

    private void enableApplicationModulesByRights(List<Right> userRightsBasedOnRoles) {
        for (Right userRight : userRightsBasedOnRoles) {
            switch (userRight.getRightName()) {
                case "PERMISSION_MANAGEMENT":
                    rightsManagement = true;
                    break;

                case "USER_MANAGEMENT":
                    userManagement = true;
                    break;

                case "BUG_MANAGEMENT":
                    bugManagement = true;
                    break;
            }
        }
    }

    public String getUserName() {
        return welcomeMessage;
    }

    public String logUserOut() {
        WebHelper.getSession().setAttribute("loggedIn", false);
        WebHelper.getSession().setAttribute("loggedInUser", null);
        WebHelper.getSession().invalidate();
        return "loginPage";
    }

    private void myRedirect(String url) {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, url);
    }
}
