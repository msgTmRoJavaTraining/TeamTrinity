package beans;

import entities.Right;
import entities.Role;
import entities.User;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@ManagedBean(name = "homeBean")
@ApplicationScoped
public class HomeBean implements Serializable {
    private String welcomeMessage = "Welcome to JBugger";

    public String getUserName() {
        return welcomeMessage;
    }
}
