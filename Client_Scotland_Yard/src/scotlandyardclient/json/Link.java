/**
 * Classe qui représente un lien entre deux stations
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.json;

import java.util.LinkedList;

public class Link {

    private final Station first;
    private final Station second;
    private final LinkedList<String> locomotions = new LinkedList<>();

    /**
     * Constructeur
     *
     * @param first Première station
     * @param second Deuxième station
     */
    public Link(Station first, Station second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Permet d'ajouter un moyen de transport empruntable sur ce lien
     *
     * @param locomotion Moyen de transport empruntable (taxi, bus, subway)
     */
    public void addLocomotion(String locomotion) {
        locomotions.add(locomotion);
    }

    /**
     * Permet d'obtenir la liste des moyens de transport empruntable sur ce lien
     *
     * @return Liste des moyens de transport empruntable sur ce lien
     */
    public LinkedList<String> getLocomotions() {
        return locomotions;
    }

    /**
     * Permet d'obtenir la première station reliée à ce lien
     *
     * @return La première station reliée à ce lien
     */
    public Station getFirst() {
        return first;
    }

    /**
     * Permet d'obtenir la deuxième station reliée à ce lien
     *
     * @return La deuxième station reliée à ce lien
     */
    public Station getSecond() {
        return second;
    }

    /**
     * Permet de savoir si le lien connecte s1 et s2
     *
     * @param s1 Une station
     * @param s2 Une station
     * @return Retourne true si s1 et s2 sont connectées par ce lien, false
     * sinon
     */
    public boolean connectStations(Station s1, Station s2) {
        return (s1 == first && s2 == second) || (s1 == second && s2 == first);
    }
}
