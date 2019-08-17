package helpers;

import entities.Right;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
public class SecurityHelper implements Serializable {
    public boolean checkUserPermissions(String permission, List<Right> userRights) {
        for (Right right : userRights) {
            if (permission.equals(right.getRightName())) {
                return true;
            }
        }

        return false;
    }

    // Functie pentru afisarea unui mesaj Growl independent de pagina web
    public void showGrowlMessage(FacesMessage.Severity severity, String title, String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);

        context.addMessage(null, new FacesMessage(severity, title, message));
    }

    // Functie pentru redirectarea spre o alta pagina
    public void customRedirectTo(String page) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
