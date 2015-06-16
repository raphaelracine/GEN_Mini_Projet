/**
 * Méthodes utiles
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */

package scotlandyardserver;

import java.util.regex.Pattern;

public class Utils {

    /**
     * Permet de vérifier qu'une chaîne de caractère respecte une expression régulière
     * @param str La chaîne de caractères à valider
     * @param pattern Expression régulière
     * @return Retourne true si la chaîne est validée par l'expression régulière, false sinon
     */
    public static boolean checkPatternMatches(String str, String pattern) {
        return Pattern.compile(pattern).matcher(str).matches();
    }

    /**
     * Permet de savoir si une chaîne de caractères est un entier
     * @param str Chaîne de caractère
     * @return Retourne true si c'est un entier, false sinon
     */
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
