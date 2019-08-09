package validators;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    public static boolean isValidEmail(String email){

        String regex = "^(.+)@msggroup.com$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber){

        String romRegex = "(\\+)40[2-8]{1}[0-9]{8}";

        String gerRegex = "(\\+)49[0-9]{9}";

        Pattern romPattern = Pattern.compile(romRegex);
        Pattern gerPattern = Pattern.compile(gerRegex);

        Matcher romMatcher = romPattern.matcher(phoneNumber);
        Matcher gerMatcher = gerPattern.matcher(phoneNumber);

        return romMatcher.matches() || gerMatcher.matches();
    }

    public static boolean areInputFieldsValid(String firstName, String lastName, String email, String telephoneNumber, List<String> roleList, String password) {
        if(!firstName.isEmpty()) {
            if(!lastName.isEmpty()) {
                if(isValidEmail(email)) {
                    if(isValidPhoneNumber(telephoneNumber)) {
                        if(roleList.size() > 0) {
                            if(!password.isEmpty()) {
                                return true;
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Wrong password", "You must enter a password"));
                            }
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"User roles missing", "You must assign roles to a user"));
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Wrong phone number", "You must enter a valid romanian or german phone number"));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Wrong email address", "You must enter a valid email address(ends with @msggroup.com)"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Wrong last name", "You must enter a last name"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Wrong first name", "You must enter a first name"));
        }

        return false;
    }

    public static String getUserName(String firstName,String lastName){

        String tmpUsername = "";


        return tmpUsername;
    }
}
