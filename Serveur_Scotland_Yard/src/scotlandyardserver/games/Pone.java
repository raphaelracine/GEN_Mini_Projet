/**
 * Cette classe représente un pion
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

public abstract class Pone {

    private Station currentStation;
    private final Client player;

    private final int ticketsTaxi;
    private final int ticketsBus;
    private final int ticketsSubway;

    /**
     * Constructeur
     *
     * @param player Le joueur qui a le pion
     * @param start La station de départ du pion
     * @param ticketsTaxi Le nombre de tickets de taxi
     * @param ticketsBus Le nombre de tickets de bus
     * @param ticketsSubway Le nombre de tickets de métro
     */
    public Pone(Client player, Station start, int ticketsTaxi, int ticketsBus, int ticketsSubway) {
        this.player = player;
        this.currentStation = start;
        this.ticketsBus = ticketsBus;
        this.ticketsSubway = ticketsSubway;
        this.ticketsTaxi = ticketsTaxi;
    }

    /**
     * Permet au pion de se déplacer dans une station
     *
     * @param dest La station de destination
     */
    public void moveInStation(Station dest) {
        this.currentStation = dest;
    }

    /**
     * Permet d'obtenir le joueur qui a ce pion
     *
     * @return Le joueur qui a ce pion
     */
    public Client getPlayer() {
        return player;
    }

    /**
     * Permet d'obtenir la station sur laquelle se trouve le pion
     *
     * @return La station sur laquelle est le pion actuellement
     */
    public Station getCurrentStation() {
        return currentStation;
    }

    /**
     * Retourne le nombre de tickets de taxi du pion
     *
     * @return Le nombre de tickets de taxi
     */
    public int getTicketsTaxi() {
        return ticketsTaxi;
    }

    /**
     * Retourne le nombre de tickets de métro du pion
     *
     * @return Le nombre de tickets de métro
     */
    public int getTicketsSubway() {
        return ticketsSubway;
    }

    /**
     * Retourne le nombre de tickets de bus du pion
     *
     * @return Le nombre de tickets de bus
     */
    public int getTicketsBus() {
        return ticketsBus;
    }
}
