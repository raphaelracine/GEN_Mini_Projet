/**
 * Classe qui représente le pion de Mister X
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.pone;

import java.awt.Color;
import scotlandyardclient.json.Station;

public class PoneMisterX extends Pone {

    private int nbBlackTickets;
    private int nbDoubleTickets;

    /**
     * Constructeur
     *
     * @param color La couleur du pion
     * @param playerName Le nom du joueur a qui appartient le pion
     * @param currentStation La station ou se trouve le pion
     * @param nbBlackTickets Le nombre de tickets noirs
     * @param nbDoubleTickets Le nombre de tickets double
     * @param nbTaxiTickets Le nombre de tickets de taxi
     * @param nbBusTickets Le nombre de tickets de bus
     * @param nbMetroTickets Le nombre de tickets de métro
     */
    public PoneMisterX(Color color, String playerName, Station currentStation, int nbBlackTickets, int nbDoubleTickets, int nbTaxiTickets, int nbBusTickets, int nbMetroTickets) {
        super(color, playerName, currentStation, nbTaxiTickets, nbBusTickets, nbMetroTickets);
        this.nbBlackTickets = nbBlackTickets;
        this.nbDoubleTickets = nbDoubleTickets;
    }

    /**
     * Permet au pion d'utiliser un ticket noir
     */
    public void useBlackTicket() {
        nbBlackTickets--;
        setChanged();
        notifyObservers();
    }

    /**
     * Permet au pion d'utiliser un ticket double
     */
    public void useDoubleTicket() {
        nbDoubleTickets--;
        setChanged();
        notifyObservers();
    }

    /**
     * Permet de savoir si c'est un pion de Mister X
     *
     * @return Retourne true si c'est le cas, false sinon
     */
    @Override
    public boolean isMisterX() {
        return true;
    }

    /**
     * Permet d'obtenir le nombre de tickets noirs du pion
     *
     * @return Le nombre de tickets noirs du pion
     */
    public int getNbBlackTickets() {
        return nbBlackTickets;
    }

    /**
     * Permet d'obtenir le nombre de tickets double du pion
     *
     * @return Le nombre de tickets double du pion
     */
    public int getNbDoubleTickets() {
        return nbDoubleTickets;
    }
}
