package backingBeans;

import ejbs.LoginEJB;
import entities.User;
import helpers.LanguagesBundleAccessor;
import helpers.NavigationHelper;
import helpers.SecurityHelper;
import lombok.Data;
import security.WebHelper;
import validators.HashingText;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;

@Data
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    @Inject
    private LoginEJB loginEJB;

    @Inject
    private LanguagesBundleAccessor languagesBundleAccessor;

    @Inject
    private NavigationHelper navigationHelper;

    @Inject
    private SecurityHelper securityHelper;

    private String username;
    private String password;

    private User toBeLoggedInUser;
    private static String lastUsername;
    private static int loginAttempts = 5;

    public void performLogin() {
        toBeLoggedInUser = new User();

        try {
            toBeLoggedInUser = loginEJB.doesAccountExist(username);

           //  If username exists
            if (toBeLoggedInUser != null) {
                //if password is correct and the account is active
                if (HashingText.getMd5(password).equals(toBeLoggedInUser.getUserLogin().getPassword()) && toBeLoggedInUser.getAccountActiveStatus()) {
                    WebHelper.getSession().setAttribute("loggedIn", true);
                    WebHelper.getSession().setAttribute("loggedInUser", toBeLoggedInUser);
                    navigationHelper.customRedirectTo("homepage.xhtml");
                } else if (HashingText.getMd5(password).equals(toBeLoggedInUser.getUserLogin().getPassword()) && !toBeLoggedInUser.getAccountActiveStatus()) {
                    //if password is correct, but the account is innactive
                    navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_WARN, languagesBundleAccessor.getResourceBundleValue("dialogMessages_loginPage_accountDeactivated_title"), languagesBundleAccessor.getResourceBundleValue("dialogMessages_loginPage_accountDeactivated_message"));
                } else if (!HashingText.getMd5(password).equals(toBeLoggedInUser.getUserLogin().getPassword()) && toBeLoggedInUser.getAccountActiveStatus()) {
                    //if the password is incorrect, but the account is still active
                    if (loginAttempts == 5) {
                        lastUsername = username;
                        navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_WARN, languagesBundleAccessor.getResourceBundleValue("dialogMessages_loginPage_wrongPassword_title"), languagesBundleAccessor.getResourceBundleValue("dialogMessage_loginPage_wrondPassword_message") + loginAttempts);
                    } else if (username.equals(lastUsername) && loginAttempts > 0) {
                        navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_WARN, languagesBundleAccessor.getResourceBundleValue("dialogMessages_loginPage_wrongPassword_title"), languagesBundleAccessor.getResourceBundleValue("dialogMessage_loginPage_wrondPassword_message") + loginAttempts);
                    } else if (username.equals(lastUsername) && loginAttempts == 0) {
                        loginAttempts = 5;
                        lastUsername = "";
                        loginEJB.deactivateAccount(toBeLoggedInUser);
                        navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("dialogMessage_loginPage_wrongPassword_accountDeactivated_title"), languagesBundleAccessor.getResourceBundleValue("dialogMessage_loginPage_wrondPassword_accountDeactivated_message"));
                    } else {
                        loginAttempts = 5;
                        lastUsername = "";
                    }

                    loginAttempts = loginAttempts - 1;
                }
            } else {
                navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("dialogMessages_loginPage_accountNotFound_title"), languagesBundleAccessor.getResourceBundleValue("dialogMessages_loginPage_accountNotFound_message"));
                navigationHelper.customRedirectTo("loginPage.xhtml");
            }
        } catch (Exception e) {
            navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("dialogMessages_loginPage_accountNotFound_title"), languagesBundleAccessor.getResourceBundleValue("dialogMessages_loginPage_accountNotFound_message"));
            navigationHelper.customRedirectTo("loginPage.xhtml");
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
