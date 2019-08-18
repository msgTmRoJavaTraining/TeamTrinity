package validators;

import helpers.LanguagesBundleAccessor;
import helpers.NavigationHelper;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ManagedBean
@SessionScoped
public class UserValidator implements Serializable {
    @Inject
    private NavigationHelper navigationHelper;

    @Inject
    private LanguagesBundleAccessor languagesBundleAccessor;

    public boolean isValidEmail(String email) {
        String regex = "^(.+)@msggroup.com$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPhoneNumber(String phoneNumber) {

        String romRegex = "(\\+)40[2-8]{1}[0-9]{8}";

        String gerRegex = "(\\+)49[0-9]{9}";

        Pattern romPattern = Pattern.compile(romRegex);
        Pattern gerPattern = Pattern.compile(gerRegex);

        Matcher romMatcher = romPattern.matcher(phoneNumber);
        Matcher gerMatcher = gerPattern.matcher(phoneNumber);

        return romMatcher.matches() || gerMatcher.matches();
    }

    public boolean areInputFieldsValid(String firstName, String lastName, String email, String telephoneNumber, List<String> roleList, String password) {
        if (!firstName.isEmpty()) {
            if (!lastName.isEmpty()) {
                if (isValidEmail(email)) {
                    if (isValidPhoneNumber(telephoneNumber)) {
                        if (roleList.size() > 0) {
                            if (!password.isEmpty()) {
                                return true;
                            } else {
                                navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_password_title"), languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_password_message"));
                            }
                        } else {
                            navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_roles_title"), languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_roles_message"));
                        }
                    } else {
                        navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_phoneNumber_title"), languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_phoneNumber_message"));
                    }
                } else {
                    navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_email_title"), languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_email_message"));
                }
            } else {
                navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_lastName_title"), languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_lastName_message"));
            }
        } else {
            navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_firstName_title"), languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_firstName_message"));
        }

        return false;
    }

    public boolean userUpdateValidFields(String email, String phoneNumber, List<String> userRoles) {
        if (isValidEmail(email)) {
            if (isValidPhoneNumber(phoneNumber)) {
                if (userRoles.size() > 0) {
                    return true;
                } else {
                    navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_roles_title"), languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_roles_message"));
                }
            } else {
                navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_phoneNumber_title"), languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_phoneNumber_message"));
            }
        } else {
            navigationHelper.showGrowlMessage(FacesMessage.SEVERITY_ERROR, languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_email_title"), languagesBundleAccessor.getResourceBundleValue("validator_userValidator_inputFields_email_message"));
        }

        return false;
    }
}
