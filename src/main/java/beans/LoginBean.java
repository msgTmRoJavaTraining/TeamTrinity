package beans;


import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import javax.persistence.*;


@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private String username;
    private String password;


    public LoginBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String performLogin() {
        if(username.equals("username") && password.equals("password")) {
            return "homepage";
        } else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Date incorecte", "Email sau parola incorecte."));
            return "";
        }
    }

    public LoginBean(){
        super();
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
