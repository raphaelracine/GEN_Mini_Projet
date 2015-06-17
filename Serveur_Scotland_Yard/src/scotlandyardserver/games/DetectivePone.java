/**
 * Cette classe représente un pion de détective
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */

package scotlandyardserver.games;

import scotlandyardserver.client.Client;
import scotlandyardserver.json.Station;

public class DetectivePone extends Pone {

    /**
     * Constructeur
     * @param player Le joueur a qui le pion appartient
     * @param start La station de départ du pion
     * @param ticketsTaxi Le nombre de ticket de taxi du pion 
     * @param ticketsBus Le nombre de ticket de bus du pion
     * @param ticketsSubway Le nombre de ticket de métro du pion
     */
    public DetectivePone(Client player, Station start, int ticketsTaxi, int ticketsBus, int ticketsSubway) {
        super(player, start, ticketsTaxi, ticketsBus, ticketsSubway);
    }
    
}
