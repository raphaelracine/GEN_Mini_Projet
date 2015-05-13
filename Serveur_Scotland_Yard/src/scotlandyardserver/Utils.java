package scotlandyardserver;

import java.util.regex.Pattern;

public class Utils {

    public static boolean checkPatternMatches(String str, String pattern) {
        return Pattern.compile(pattern).matcher(str).matches();
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
