/**
 * Cette classe représente un pion de Mister X
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

public class MisterXPone extends Pone {

    private final int ticketsDouble;
    private final int ticketsBlack;

    /**
     * Constructeur
     *
     * @param player Le joueur qui a ce pion
     * @param start La station de départ du pion
     * @param ticketsTaxi Le nombre de tickets de taxi
     * @param ticketsBus Le nombre de tickets de bus
     * @param ticketsSubway Le nombre de tickets de métro
     * @param ticketsBlack Le nombre de tickets noirs
     * @param ticketsDouble Le nombre de tickets coup double
     */
    public MisterXPone(Client player, Station start, int ticketsTaxi, int ticketsBus, int ticketsSubway, int ticketsBlack, int ticketsDouble) {
        super(player, start, ticketsTaxi, ticketsBus, ticketsSubway);
        this.ticketsBlack = ticketsBlack;
        this.ticketsDouble = ticketsDouble;
    }
}
