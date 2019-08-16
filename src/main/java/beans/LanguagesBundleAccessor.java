package beans;

import lombok.Data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

@Data
@ManagedBean
@SessionScoped
public class LanguagesBundleAccessor implements Serializable {
    @Inject
    private LanguageBean languageBean;

    private Locale beanLocale;

    @PostConstruct
    public void init() {
        beanLocale = new Locale(languageBean.getLanguage());
    }

    public String getResourceBundleValue(String resourceTitle) {
        return ResourceBundle.getBundle("internalization.languages", beanLocale).getString(resourceTitle);
    }
}
