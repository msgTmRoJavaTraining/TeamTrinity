package beans;

import entities.Right;
import entities.Role;
import entities.User;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

@Named
@ApplicationScoped
public class HomeBean implements Serializable {
    private String welcomeMessage = "Welcome to JBugger";

    Locale locale;

    public String getUserName() {
        return welcomeMessage;
    }


}
