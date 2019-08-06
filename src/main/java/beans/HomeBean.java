package beans;

import lombok.Getter;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@Getter
@ManagedBean(name = "homeBean")
@ApplicationScoped
public class HomeBean implements Serializable {
    private boolean isAdmin = false;    //Facem sa dispara butonul de Rights Management
}
