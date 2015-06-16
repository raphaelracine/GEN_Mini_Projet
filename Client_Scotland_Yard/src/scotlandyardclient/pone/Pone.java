/**
 * Classe qui représente le pion d'un détective.
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.pone;

import java.awt.Color;
import java.util.Observable;
import scotlandyardclient.Client;
import scotlandyardclient.json.Station;

public class Pone extends Observable {

    private int nbTaxiTickets;
    private int nbBusTickets;
    private int nbMetroTickets;

    private final Color color;
    private final String playerName;
    private Station currentStation;

    /**
     * Constructeur
     *
     * @param color La couleur du pion
     * @param playerName Le nom du joueur a qui appartient le pion
     * @param currentStation La station où le pion se trouve actuellement
     * @param nbTaxiTickets Nombre de tickets de taxi
     * @param nbBusTickets Nombre de tickets de bus
     * @param nbMetroTickets Nombre de tickets de metro
     */
    public Pone(Color color, String playerName, Station currentStation, int nbTaxiTickets, int nbBusTickets, int nbMetroTickets) {
        Client c = Client.getInstance();

        if (playerName.equals(c.username())) {
            c.setPone(this);
        }

        this.color = color;
        this.playerName = playerName;
        this.currentStation = currentStation;
        currentStation.setPone(this);
        this.nbTaxiTickets = nbTaxiTickets;
        this.nbBusTickets = nbBusTickets;
        this.nbMetroTickets = nbMetroTickets;
        setChanged();
        notifyObservers();
    }

    /**
     * Permet d'obtenir le nom du joueur qui a ce pion
     *
     * @return Nom du joueur qui a le pion
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Permet d'obtenir le nombre de tickets de taxi du pion
     *
     * @return Nombre de tickets de taxi du pion
     */
    public int getNbTaxiTickets() {
        return nbTaxiTickets;
    }

    /**
     * Permet d'obtenir le nombre de tickets de bus du pion
     *
     * @return Nombre de tickets de bus du pion
     */
    public int getNbBusTickets() {
        return nbBusTickets;
    }

    /**
     * Permet d'obtenir le nombre de tickets de métro du pion
     *
     * @return Nombre de tickets de métro du pion
     */
    public int getNbMetroTickets() {
        return nbMetroTickets;
    }

    /**
     * Permet d'obtenir la station sur laquelle se trouve le pion
     *
     * @return Station sur laquelle se trouve le pion
     */
    public Station getCurrentStation() {
        return currentStation;
    }

    /**
     * Permet d'obtenir la couleur du pion
     *
     * @return La couleur du pion
     */
    public Color getColor() {
        return color;
    }

    /**
     * Permet de déplacer le pion d'une station à l'autre
     *
     * @param destination Station de destination
     */
    public void moveToStation(Station destination) {
        currentStation.setPone(null);
        currentStation = destination;
        currentStation.setPone(this);

        setChanged();
        notifyObservers();
    }

    /**
     * Permet au pion d'utiliser un ticket de taxi
     */
    public void useTaxiTicket() {
        nbTaxiTickets--;
        setChanged();
        notifyObservers();
    }

    /**
     * Permet au pion d'utiliser un ticket de bus
     */
    public void useBusTicket() {
        nbBusTickets--;
        setChanged();
        notifyObservers();
    }

    /**
     * Permet au pion d'utiliser un ticket de métro
     */
    public void useMetroTicket() {
        nbMetroTickets--;
        setChanged();
        notifyObservers();
    }

    /**
     * Permet de savoir si c'est un pion de Mister X ou pas
     *
     * @return Retourne true si c'est le pion de mister X, false sinon
     */
    public boolean isMisterX() {
        return false;
    }
}
