package beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Locale;

@ManagedBean(name = "applicationLanguage")
@SessionScoped
public class LanguageBean implements Serializable {
    private Locale locale;

    @PostConstruct
    public void init() {
        locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage().toUpperCase();
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(language));
    }
}
