/**
 * Méthodes utiles du programme client
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient;

import java.util.regex.Pattern;

public class Utils {

    /**
     * Permet de valider une chaîne de caractères par le biais d'une expression
     * régulière
     *
     * @param str La chaîne de caractère à valider
     * @param pattern L'expression régulière a vérifier sur str
     * @return Retourne true si la chaîne de caractères est validée, false sinon
     */
    public static boolean checkPatternMatches(String str, String pattern) {
        return Pattern.compile(pattern).matcher(str).matches();
    }

    /**
     * Permet de parser une commande reçu du serveur (delimiteur : caractère #)
     *
     * @param command La commande à parser
     * @return Un tableau de string avec comme premier élément le nom de la
     * commande, ensuite les paramètres
     */
    public static String[] parseCommand(String command) {
        return command.split("#");
    }
}
