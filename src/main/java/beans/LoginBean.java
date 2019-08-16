package beans;

import backingBean.DatabaseLoginEJB;
import entities.Right;
import entities.Role;
import entities.User;
import lombok.Data;
import security.WebHelper;
import validators.HashingText;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ManagedBean(name = "loginBean")
@ApplicationScoped
public class LoginBean implements Serializable {
    @Inject
    private DatabaseLoginEJB databaseLoginEJB;

    private String username;
    private String password;

    private User toBeLoggedInUser;

    public String performLogin() {
        toBeLoggedInUser = new User();

        try {
            toBeLoggedInUser = databaseLoginEJB.loginUserByUsernamePassword(username, HashingText.getMd5(password));

            if (toBeLoggedInUser != null && toBeLoggedInUser.getAccountActiveStatus()) {
                WebHelper.getSession().setAttribute("loggedIn", true);
                WebHelper.getSession().setAttribute("loggedInUser", toBeLoggedInUser);

                return "homepage";
            } else if( toBeLoggedInUser != null && !toBeLoggedInUser.getAccountActiveStatus()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "#{msg.dialogMessages_loginPage_accountDeactivated_title}", "#{msg.dialogMessages_loginPage_accountDeactivated_message}"));
                return "";
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "#{msg.dialogMessages_loginPage_accountNotFound_title}", "#{msg.dialogMessages_loginPage_accountNotFound_message}"));
        }

        return "";
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

    public User getToBeLoggedInUser() {
        return toBeLoggedInUser;
    }

    public void setToBeLoggedInUser(User toBeLoggedInUser) {
        this.toBeLoggedInUser = toBeLoggedInUser;
    }

    public void navigateTo(String page) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
