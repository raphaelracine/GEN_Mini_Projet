package scotlandyardclient;

import java.util.regex.Pattern;

public class Utils {

    public static boolean checkPatternMatches(String str, String pattern) {
        return Pattern.compile(pattern).matcher(str).matches();
    }
    
    public static String[] parseCommand(String command) {
        return command.split("#");
    }
}
