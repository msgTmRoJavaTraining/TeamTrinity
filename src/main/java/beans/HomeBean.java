package beans;

import entities.Right;
import entities.Role;
import entities.User;
import lombok.Getter;
import security.WebHelper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@ManagedBean(name = "homeBean")
@ApplicationScoped
public class HomeBean implements Serializable {
    private String welcomeMessage = "Welcome to JBugger"; //+ WebHelper.getSession().getAttribute("loggedInUserId");

    public String getUserName() {
        return welcomeMessage;
    }

    public String logUserOut() {
        WebHelper.getSession().setAttribute("loggedIn",false);
        WebHelper.getSession().invalidate();
        return "loginPage";
    }

//    @ManagedProperty(value = "loginBean")
//    private LoginBean loginBean;
//
//    public void setLoginBean(LoginBean loginBean) {
//        this.loginBean = loginBean;
//    }
//
//    public String getUserName() {
//        return "Welcome to JBugger " + loginBean.getUserId();
//    }
//
//    public String logUserOut() {
//        WebHelper.getSession().setAttribute("loggedIn",false);
//        WebHelper.getSession().invalidate();
//        return "loginPage";
//    }
}
