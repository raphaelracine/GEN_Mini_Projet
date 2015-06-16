/**
 * Classe qui représente une station.
 *
 * @author Raphaël Racine
 * @author Yassin Kammoun
 * @author Vanessa Michelle Meguep
 *
 * @date 16.05.2015
 */
package scotlandyardclient.json;

import scotlandyardclient.pone.Pone;

public class Station {

    private final int numero;
    private final String type;
    private final int id;
    private final int x;
    private final int y;

    private Pone pone; // Pion qui se trouve sur la station, s'il y en a un

    /**
     * Constructeur
     *
     * @param id Identifiant unique de la station
     * @param numero Numéro de la station sur la carte
     * @param type Type de station (taxi, taxibus, taxibussubway)
     * @param x Coordonnée x de la station
     * @param y Coordonnée y de la station
     */
    public Station(int id, int numero, String type, int x, int y) {
        this.numero = numero;
        this.type = type;
        this.id = id;
        this.y = y;
        this.x = x;
    }

    /**
     * Permet d'obtenir l'identifiant unique de la station
     *
     * @return Identifiant unique de la station
     */
    public int getId() {
        return id;
    }

    /**
     * Permet d'obtenir le numéro de la station sur la carte
     *
     * @return Numéro de la station sur la carte
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Permet d'obtenir le type de station
     *
     * @return Type de station
     */
    public String getType() {
        return type;
    }

    /**
     * Permet d'obtenir la coordonnées x de la station
     *
     * @return Coordonnée x de la station
     */
    public int getX() {
        return x;
    }

    /**
     * Permet d'obtenir la coordonnée y de la station
     *
     * @return Coordonnée y de la station
     */
    public int getY() {
        return y;
    }

    /**
     * Permet de placer ou d'enlever un pion sur la station
     *
     * @param p Pion à placer sur la station (mettre null pour l'enlever)
     */
    public void setPone(Pone p) {
        this.pone = p;
    }

    /**
     * Permet d'obtenir le pion qui se trouve sur la station
     *
     * @return Pion qui se trouve sur la station (null si aucun pion ne s'y
     * trouve)
     */
    public Pone getPone() {
        return pone;
    }
}
