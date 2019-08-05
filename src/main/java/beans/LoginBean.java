package beans;

import lombok.Data;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@Data
@Named
@ApplicationScoped
public class LoginBean implements Serializable {
    private String username;
    private String password;

    public String performLogin() {
        if(username.equals("username") && password.equals("password")) {
            return "homepage";
        } else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Date incorecte", "Email sau parola incorecte."));
            return "";
        }
    }
}
