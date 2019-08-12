package beans;

import backingBean.DatabaseLoginEJB;
import entities.User;
import security.WebHelper;
import validators.HashingText;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean(name = "loginBean")
@ApplicationScoped
public class LoginBean implements Serializable {
    @Inject
    private DatabaseLoginEJB databaseLoginEJB;

    private String username;
    private String password;

    private User toBeLoggedInUser;
    private int userId;

    public String performLogin() {
        toBeLoggedInUser = new User();
       try {
         //  toBeLoggedInUser = databaseLoginEJB.loginUserByUsernamePassword(username, HashingText.getMd5(password));

           if (username.equals("username") && password.equals("password")             /*toBeLoggedInUser != null*/) {
               WebHelper.getSession().setAttribute("loggedIn", true);
               //WebHelper.getSession().setAttribute("loggedInUser", toBeLoggedInUser);
               return "homepage";
           } else {
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Date incorecte", "Email sau parola incorecte."));
               return "";
           }
       }catch (Exception e){
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Date incorecte", "Email sau parola incorecte."));

       }
       return "";
    }


    //Ionut
    //Borozan

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

    public String navigateTo(String page) {
        return page;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
