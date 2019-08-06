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
import java.util.List;

@ManagedBean(name = "loginBean")
@ApplicationScoped
public class LoginBean implements Serializable {
    private String username;
    private String password;

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
}
