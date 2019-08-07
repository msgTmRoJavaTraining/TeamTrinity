package validators;

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
}
