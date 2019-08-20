package backingBeans;

import helpers.CookieHelper;
import lombok.Data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import java.io.Serializable;

@Data
@ManagedBean(name = "applicationLanguage")
@SessionScoped
public class LanguageBean implements Serializable {
    private String language = "en";
    private Cookie languageCookie;

    @Inject
    private CookieHelper cookieHelper;

    @PostConstruct
    public void initBean() {
        language = "en";

        if ((languageCookie = cookieHelper.getCookie("JBuggerLanguageCookie")) != null) {
            language = languageCookie.getValue();
        }
    }

    public void setLanguage(String selectedLanguage) {
        language = selectedLanguage;
        cookieHelper.setCookie("JBuggerLanguageCookie", selectedLanguage, 60 * 60 * 24 * 30 * 12);    //setat pentru 1 an
    }

    public void putLanguageToEnglish() {
        language = "en";
        cookieHelper.setCookie("JBuggerLanguageCookie", "en", 60 * 60 * 24 * 30 * 12);    //setat pentru 1 an
    }

    public void putLanguageToRomanian() {
        language = "ro";
        cookieHelper.setCookie("JBuggerLanguageCookie", "ro", 60 * 60 * 24 * 30 * 12);    //setat pentru 1 an
    }
}
